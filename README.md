<div align="center">
  <h1>SHOP MANAGEMENT PROJECT 🏪</h1>
</div>

## Description:
An ongoing project of a shop management system designed to facilitate product organization within a retail environment.  
The application supports essential functions such as adding, updating, and removing products from inventory. Additionally, it integrates with a MySQL database for efficient data storage and retrieval, providing a robust solution for small-sized shops.

<div style="margin: 20px 0; border: 1px solid #ccc; padding: 10px;">

## Table of Contents:

- [Features](#features)
- [Installation](#installation)
- [Requirements](#requirements)
- [Usage](#usage)
- [Contact](#contact)
</div>

## Features:
- **Product Management**: Add, update, and remove products in the shop.
- **Inventory Management**: Retrieve and display current inventory, enabling efficient tracking of available products.
- **Cash Management System**: Tracks and manages cash flow generated from sales.
- **Dynamic Inventory Updates/Tracking**: Ensures real-time reflection of product availability.
- **MySQL Integration**: Provides efficient data storage and retrieval through a MySQL database.
- **User-Friendly Interface**: Intuitive graphical interface powered by Windows graphics for easy navigation.
- **Credential Validation**: Secures user access through credential validation to protect sensitive information.

## Installation:
1. Clone the repository:

```
git clone https://github.com/Dynavy/SHOP-PROJECT
```
2. Navigate to the project directory:
```
cd SHOP-PROJECT
```
3. Build the project with Maven (ensure you have Maven installed): 
```
bash mvn clean install
```
4. Ensure MySQL is installed and running. - Configure the database connection with your own credentials. - Create a new database in MySQL:<br>
```
sql CREATE DATABASE ShopDB
````
5. Run the application:
```
mvn exec
```

## Requirements:
- **Windows Builder**: To run and design the graphical interface, make sure to have Windows Builder installed in your IDE.
- **SQL Dependency**: Ensure that the `pom.xml` file includes the SQL dependency to execute SQL queries correctly.

## Usage:
To use the Shop Management Project, run the application after completing the installation steps. The user interface will allow you to manage products, view inventory, track cash flow and other functionalities.

## Contact:
For questions or suggestions, feel free to reach out:
- Name: Dylan Navarro Vinyarta.
- Email: [dynavy@hotmail.com].
