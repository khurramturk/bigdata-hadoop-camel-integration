#File routing using Camel and ActiveMQ.

# File Processing #

We will be doing file processing on different system modules. The integration of the these different system modules is done through Apache Camel and ActiveMQ.

We have two interfaces from where the file is being uploaded to the system, one is HTTP interface and the other is FTP. From both of the interfaces, file is routed to two different queues named as "activemq:ftpinfile" and "activemq:httpinfile"

Through web front-end, we will upload the file and submit it to the FileReaderService.java, from where it will be picked by Apache Camel and put to the HTTP file route.
When we run Startup.java as java application, 2 consumers for the above routes will be available "activemq:httpinfile" and "activemq:ftpinfile".

On the other end, file will be retrieved from the queue "activemq:httpinfile " using Apache Camel, and sent storage handler to save the file into the database (MongoDB)as it is. At the same time we will also route this file to new queue “seda:import” from where it will be retrieved for further processing “like splitting the file data to canonical fields, records and chunks”. Now processed data is ready to be saved in MongoDB, So once again it will be passed to storage handler to store it into the database.

Camel comes into action and now parallel processing starts, sending processed data to storage (MongoDB) and for further processing at Hadoop at the same time.