# api-abc-kiara
Este proyecto permite buscar noticias del portal ABC y devolverlas en formato JSON a través de un endpoint REST.
- Stack utiliado
  - VS code 
  - Java 17
  - Maven
  - Spring Boot (incluido en el proyecto a través de Maven)
- Pasos de ejecucion
1. Clonar el proyecto
- git clone https://github.com/kiararivarola/api-abc-kiara.git
- cd api-abc-kiara
2. Configurar ChromeDriver (utilizado para obtener las noticias)
- C:\ruta\a\chromedriver.exe
3. Ejecutar el proyecto, la API estará disponible en http://localhost:8080
- mvn spring-boot:run
4. Test de la API
Desde Chrome o Postman (realizando un GET)
- Ejemplo de busqueda: nenecho
http://localhost:8080/consulta?q=nenecho
- Ejemplo de respuesta:
<img width="979" height="625" alt="image" src="https://github.com/user-attachments/assets/67046024-3b87-4167-a018-6aebb1d354ca" />

- Otras respuestas
<img width="1482" height="705" alt="image" src="https://github.com/user-attachments/assets/04525d06-f564-4394-bd7d-930ed73286bf" />
<img width="1473" height="641" alt="image" src="https://github.com/user-attachments/assets/dabfe043-ddff-4467-9ad3-f9da69b046bc" />
- cURL
  - curl --location 'http://localhost:8080/consulta?q='
- No se pudo implementar los puntos mencionados en el apartado BONUS de la solicitud de realización del proyecto :c


