const verProductos = document.getElementById("verProductos");
const botonver = document.getElementById("boton");
const botonguardar = document.getElementById("botong");
const gdescripcion = document.getElementById("descripcion");
const gprecio = document.getElementById("precio");
const eliminarboton = document.getElementById("eliminarboton");
const ideliminar = document.getElementById("eliminarid");
const nuevoprecio = document.getElementById("newprecio");
const nuevadescripcion = document.getElementById("newdescripcion");
const botonactualizar = document.getElementById("botonactualizar");
const idactualizar = document.getElementById("idactualizar");

botonver.addEventListener('click', ()=>{
    fetch("http://localhost:8080/api/amigurumis").then(
        res => res.json()
    ).then(amigurumi =>{
        verProductos.innerHTML = '';
        if(Array.isArray(amigurumi)){
            amigurumi.forEach( a =>{
                let li = document.createElement('li');
                li.textContent = a.descripcion +" | id: " + a.id;
                verProductos.appendChild(li);
            })
        }else if (amigurumi.mensaje){
            let li = document.createElement('li');
            li.textContent = amigurumi.mensaje;
            verProductos.appendChild(li);
        }

    }).catch(error => alert("Error al cargar:" +error))

})


botonguardar.addEventListener('click', () =>{
    let price  = gprecio.value;
    let description = gdescripcion.value;
    if(price <0 || price === ""){
        alert("Ingresa Un precio mayor a 0")
        return;
    }
    if(description.trim===0){
        alert("Por favor ingresa una descripcion valida")
        return;
    }
    let amigurumi ={
        descripcion : description,
        precio : price
    }
    fetch("http://localhost:8080/api/amigurumis",{
        method : "POST",
        headers : {"Content-Type": "application/json"},
        body: JSON.stringify(amigurumi)
    }).then(res => {
        if(res.ok ){
            return res.json();
        }throw new Error("error");
    }).then(respuesta =>{
        alert(respuesta.mensaje)
    }).catch(error =>{alert("Error al agregar")})

})

eliminarboton.addEventListener('click', ()=>{
    let id = ideliminar.value;
    if(id <= 0){
        alert("Ingresa un id valido porfavor")
        return;
    }
    fetch(`http://localhost:8080/api/amigurumis?id=${id}`,{
        method : "DELETE"
    }).then(res =>{
            if(res.ok){
                return res.json()
            } throw new Error();
    }).then(respuesta =>{
        alert(respuesta.mensaje)
    }).catch(error => {alert(error)})
})


botonactualizar.addEventListener('click', ()=>{
    let description = nuevadescripcion.value
    let price = nuevoprecio.value
    let id = idactualizar.value

    if(id < 0 || id === ""){
        alert("Ingresa un id valido")
        return;
    }
    if(price <0 || price === ""){
        alert("Ingresa Un precio mayor a 0")
        return;
    }
    if(description.trim===0){
        alert("Por favor ingresa una descripcion valida")
        return;
    }

    let amigurumi ={
        descripcion : description,
        precio : price
    }
    fetch(`http://localhost:8080/api/amigurumis?id=${id}`,{
        method : "PUT",
        headers : {"Content-Type" :"application/json"},
        body : JSON.stringify(amigurumi)
    }).then(res=>{
        if(res.ok){
            return res.json()
        } throw new Error();
    }).then(respuesta =>{
        alert(respuesta.mensaje)
    }).catch(error =>{
        alert(error)
    })
})










