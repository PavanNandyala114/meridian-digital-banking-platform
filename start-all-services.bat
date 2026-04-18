@echo off
cd /d %~dp0

start "Account Service" cmd /k "cd /d account-service && mvnw spring-boot:run"
start "Customer Service" cmd /k "cd /d customer-service && mvnw spring-boot:run"
start "Payment Service" cmd /k "cd /d payment-service && mvnw spring-boot:run"