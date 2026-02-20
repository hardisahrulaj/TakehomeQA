@echo off
set NODE="C:\Program Files\nodejs\node.exe"
set NEWMAN=%APPDATA%\npm\node_modules\newman\bin\newman.js
set COLLECTION=petstore_collection.json
set ENVIRONMENT=petstore_environment.json

if not exist reports mkdir reports

%NODE% %NEWMAN% run %COLLECTION% -e %ENVIRONMENT% -r cli,htmlextra,json --reporter-htmlextra-export reports/report.html --reporter-json-export reports/report.json
