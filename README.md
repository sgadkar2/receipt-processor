After cloning the project run the following commands 

1) Run the following command for creating docker image from docker file
   sudo docker build --tag=receipt-processor:latest .

2) Run the following command for running the image file
   sudo docker run -p8080:8080 receipt-processor:latest

The API is running on port 8080