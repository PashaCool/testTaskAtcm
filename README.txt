Definition rest-controllers

1) DatabaseDetailController

    - GET /api/connection/all - returns all saved connections

    - POST /api/connection/create - saves new connection. Consumes JSON like: {
                                                                                 "name": "train_station",
                                                                                 "hostName": "192.1.3.10",
                                                                                 "port": 1213,
                                                                                 "databaseName": "bus_tickets",
                                                                                 "userName": "driver",
                                                                                  "password": "drive12"
                                                                             }

    - DELETE /api/connection/{uuid} - applies uuid entity for delete

    - PUT/PATCH /api/connection/update - applies JSON and update entity if match uuid, otherwise save new entity

    - GET /api/connection/{uuid} - finds entity by uuid

2) ExternalConnectionController

    - POST /api/external/databaseMetaData - applies data for connection and return tables and columns definition. Request body looks like: {
                                                                            "name": "someNameDb",
                                                                             "hostName": "localhost",
                                                                             "port": 5432,
                                                                             "databaseName": "testAtaccamaDb",
                                                                             "userName": "postgres",
                                                                             "password": "postgres"
                                                                         }

    - POST /api/external/query - applies connection definition, list of columns and table/view and return data. Request body looks like: {
                                                                      "connectionDefinition": {
                                                                          "name": "name",
                                                                          "hostName": "localhost",
                                                                          "port": 5432,
                                                                          "databaseName": "externaldbproduct",
                                                                          "userName": "postgres",
                                                                          "password": "postgres"
                                                                      },
                                                                      "columns": "id, product_title, product_price, exists_count",
                                                                      "dataSource": "prd.products"
                                                                  }

3) ShutDownController

    - POST /shutdownContext - closes application context