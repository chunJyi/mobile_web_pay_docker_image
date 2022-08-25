<h1 align="center">Hi , I'm Toe Chun</h1>
<h3 align="center">A passionate Java developer from Myanmar</h3>

-  Im currently working on **Pay_app**

-  How to reach me **moethuchun@gmail.com**

<h3 align="left">Connect with me:</h3>
<p align="left">
</p>
# pay_app
Docker profile

https://hub.docker.com/r/chunjyi


# <h2>Steps to Setup</h2>

#### 1. pull docker image


      sudo docker push chunjyi/mobilewebpay:latest

      
#### 2. create database using PostgreSQL and  insert root user 

      create database pay_appdb;
      
    
#### 2. run docker 

    sudo docker run  --net=host --name pay_app_v1  -p 8080:8080 chunjyi/mobilewebpay:latest


#### 3. Explore

    http://localhost:8080/
