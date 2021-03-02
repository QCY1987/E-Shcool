Запуск базы с помошью докера

1.Установка докера
    1)https://docs.docker.com/get-docker/

2. Для создания локального контейнера Docker Postgres DB выполнить команду в консоле
   1)docker run --name my-pg -e POSTGRES_DB=jm_e_school -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -p 5435:5432 -d postgres 

