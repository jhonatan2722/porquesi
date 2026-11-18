package server;
import com.fasterxml.jackson.core.JsonProcessingException;
import dao.*;
import com.sun.net.httpserver.*;
import models.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Servidor {

    public void conectarServidor(){

        try{
         HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
         server.createContext("/api/amigurumis", new manejarAmigurumis());
         server.setExecutor(Executors.newCachedThreadPool());
         server.start();
        }catch (IOException e){
            System.out.println(e);
          }
       }

    static class manejarAmigurumis implements  HttpHandler {
        AmigurumiDao am = new AmigurumiDao();
        @Override
        public void handle(HttpExchange navegador) throws  IOException{

            navegador.getResponseHeaders().set("Access-Control-Allow-Origin","*");
            navegador.getResponseHeaders().set("Access-Control-Allow-Methods","PUT,DELETE,POST,GET,OPTIONS");
            navegador.getResponseHeaders().set("Access-Control-Allow-Headers","Content-Type");
            navegador.getResponseHeaders().set("Content-Type","application/json; charset=UTF-8");
            String metodo = navegador.getRequestMethod();

            if("OPTIONS".equalsIgnoreCase(metodo)){
              try{
                navegador.sendResponseHeaders(204,-1);
                navegador.close();
                return;
              }catch(IOException e){
                  throw new RuntimeException(e);
              }
            }
              ObjectMapper mapper = new ObjectMapper();
              if(metodo.equals("GET")){
                  String json = "";
                  ArrayList<Amigurumi>amigurumis = am.verAmigurumis();
                  if(amigurumis.isEmpty()){
                           json = "{\"mensaje\":\"No hay productos disponibles\"}";
                  }else{
                      json = mapper.writeValueAsString(amigurumis);
                  }
                      try{
                          navegador.sendResponseHeaders(200,json.getBytes(StandardCharsets.UTF_8).length);
                          OutputStream os = navegador.getResponseBody();
                          os.write(json.getBytes(StandardCharsets.UTF_8));
                          os.flush();
                          os.close();
                      }catch(JsonProcessingException e){
                          throw  new RuntimeException(e);
                      }catch(IOException e){
                          throw new RuntimeException(e);
                      }
                  }
              if(metodo.equals("POST")){
                  try{

                      InputStream input = navegador.getRequestBody();
                      Amigurumi amigurumi = mapper.readValue(input, Amigurumi.class);
                      am.guardarAmigurumis(amigurumi);
                      String mensaje = "{\"mensaje\":\"Producto guardado correctamente\"}";
                      navegador.sendResponseHeaders(201,mensaje.getBytes(StandardCharsets.UTF_8).length);
                      OutputStream os = navegador.getResponseBody();
                      os.write(mensaje.getBytes(StandardCharsets.UTF_8));
                      os.close();
                  } catch (IOException e) {
                  }
              }

              if(metodo.equals("PUT")){
                  InputStream input = navegador.getRequestBody();
                  String query = navegador.getRequestURI().getQuery();
                  int id = 0;
                  if(query!= null && query.startsWith("id")){
                      id = Integer.parseInt(query.split("=")[1]);
                  }
                  Amigurumi amigurumi = mapper.readValue(input, Amigurumi.class);
                  int filas =  am.actualizarAmigurumis(amigurumi,id);
                  String mensaje = "";
                  if(filas<1){
                      mensaje = "{\"mensaje\":\"No existe amigurumi con el id ingresado\"}";
                  }else{
                      mensaje = "{\"mensaje\":\"Producto actualizado correctamente\"}";
                  }

                  navegador.sendResponseHeaders(201,mensaje.getBytes(StandardCharsets.UTF_8).length);
                  OutputStream os = navegador.getResponseBody();
                  os.write(mensaje.getBytes(StandardCharsets.UTF_8));
                  os.flush();
                  os.close();
              }

              if(metodo.equals("DELETE")){
                  String query = navegador.getRequestURI().getQuery();
                  int id = 0;
                  if(query != null && query.contains("id=")){
                      String valorid = query.split("=")[1];
                      id = Integer.parseInt(valorid);
                  }
                  int filas = am.borrarAmigurumis(id);
                  String mensaje = "";
                  if(filas == 0 ) {
                      mensaje = "{\"mensaje\":\"No se encontro producto con el id ingresado\"}";
                  }else{
                      mensaje = "{\"mensaje\":\"Se elimino producto de manera exitosa\"}";
                  }
                  navegador.sendResponseHeaders(200,mensaje.getBytes(StandardCharsets.UTF_8).length);
                  OutputStream os = navegador.getResponseBody();
                  os.write(mensaje.getBytes(StandardCharsets.UTF_8));
                  os.flush();
                  os.close();
              }



              }
            }


        }


