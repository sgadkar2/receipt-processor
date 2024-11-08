# Instructions to run the application
This solution uses Java with Spring Boot. As provided in the instructions I am assuming you have Docker set up, so I have provided a Dockerfile which will set up a container to run the application. Please follow the steps:

1. Clone this repository:
    ```bash
    git clone https://github.com/sgadkar2/receipt-processor.git
    ```

2) Build the docker image:
```bash
   sudo docker build --tag=receipt-processor:latest .
```

3) Run the Docker Container:
```bash
   sudo docker run -p9090:8080 receipt-processor:latest
```

The API is running on port 9090. Please note, if you receive an error similar to `Bind for 0.0.0.0:9090 failed: port is already allocated.` you can replace 9090 in the above command with an unallocated port on your machine.

4) Access the endpoints - you can set this up via a client of your choice like Postman, but here are examples using cURL. Here is a link to install cURL if needed: https://everything.curl.dev/install/index.html
   Please adjust the port accordingly, if you needed to use a different port in the step above.
   1. To make a POST request to submit a receipt, you can use the following cURL command, replacing the JSON receipt body as appropriate for your use 
      ```shell
      curl --location 'http://localhost:9090/receipts/process' \
      --header 'Content-Type: application/json' \
      --data '
      {
         "retailer": "Target",
         "purchaseDate": "2022-01-02",
         "purchaseTime": "13:13",
         "total": "1.25",
         "items": [
            {
               "shortDescription": "Pepsi - 12-oz",
               "price": "1.25"
            }
         ]
      }'
      ```
   2. To make a GET request to lookup the points for a given receiptId, you can use the following cURL command, replacing the `id` in `/receipts/{id}/points` as appropriate for your use
   ```shell
      curl --location 'http://localhost:9090/receipts/1d0f3578-8dbf-4c58-bd99-4d609fba9280/points'
      ```