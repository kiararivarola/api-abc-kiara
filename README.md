# api-abc-kiara
Este proyecto permite buscar noticias del portal ABC y devolverlas en formato JSON a través de un endpoint REST.
Stack utiliado
-Java 17
-Maven
-Spring Boot (incluido en el proyecto a través de Maven)
Pasos de ejecucion
1. Clonar el proyecto
git clone https://github.com/kiararivarola/api-abc-kiara.git
cd api-abc-kiara
2. Configurar ChromeDriver (utilizado para obtener las noticias)
C:\ruta\a\chromedriver.exe
3. Ejecutar el proyecto, la API estará disponible en http://localhost:8080
mvn spring-boot:run
4. Test de la API
Desde Chrome o Postman (realizando un GET)
Ejemplo de busqueda: nenecho
http://localhost:8080/consulta?q=nenecho
Ejemplo de respuesta:
<img width="979" height="625" alt="image" src="https://github.com/user-attachments/assets/67046024-3b87-4167-a018-6aebb1d354ca" />


