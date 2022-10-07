# geektrust
mymoney application

Build application:
mvn clean install -DskipTests -q assembly:single

Running application:
java -jar <path_to>/geektrust.jar <absolute_path_to_input_file>

Example: Inside the geektrust folder, the following command will work
java -jar target/geektrust.jar sample1.txt

Running the tests:
mvn clean test
