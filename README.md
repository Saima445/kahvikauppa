# Coffee shop app

## Project description

Coffee Shop is a versatile web application designed to provide an intuitive platform for both the management of the coffee shop and customers, allowing users to browse, order, and manage products with ease. The interface is custom-designed to ensure a seamless and user-friendly experience throughout the app. The clean and consistent style is reflected in every part of the application. The app features a wide range of functionalities, including product browsing, efficient product management, customer relationship management (VIP registration), and order processing.

## Features

### Product management

The admin user can add, edit, and delete products (linked to an H2 database). Each product includes detailed information such as name, price, description, product image, and an assigned manufacturer, supplier, and department. Saved products can easily be edited or deleted.

- Add new products, define their name, price, and description.
- Edit existing products as needed.
- Remove products from the selection and database.
- 
### Supplier and manufacturer management

The admin user can add, edit, and delete suppliers and manufacturers (linked to an H2 database). Each supplier and manufacturer has detailed information such as name, contact person, and website. Saved details can be easily modified or deleted.

- Add new suppliers and manufacturers, specifying their name, contact info, and website.
- Edit supplier and manufacturer details.
- Delete suppliers and manufacturers from the selection and database.

### Department management

The admin user can add, edit, and delete departments (linked to an H2 database). Each department is assigned a Department IDP (parent id), which helps create a hierarchical structure in the database. Departments allow for efficient organization and grouping of products, making it easier for customers to find items and for products to be listed on their respective HTML pages.

- Create new departments, define their name and department IDP (parent id).
- Edit department details.
- Remove departments from the selection and database.

### VIP Customer management

The app allows customers to register as VIPs, and the admin can manage these VIP customers (linked to an H2 database) within the app.

- Register new VIP customers, specifying their first name, last name, and email.
- Remove VIP customers as needed.

### User management

Access to the Coffee Shop admin panel requires login with admin credentials. The login process ensures that only authorized personnel can access the panel and perform administrative tasks.

- Users enter their username and password on the login form.
- The credentials are securely sent to the server.
- The server validates the credentials and ensures they are correct.
- If authentication is successful, the user is redirected to the admin panel to perform various tasks.
- If authentication fails, the user receives an error message and is prompted to try again.

The login functionality is implemented using the **Spring Security** library, ensuring strong authentication and authorization mechanisms for web applications. This feature is integrated into the Coffee Shop backend, validating credentials before granting access to the admin panel.

- Logging in with admin credentials enables access to the admin panel.

## Technologies and libraries

The project leverages a range of Java-based technologies and components from the **Spring Framework** to build the backend. The frontend uses traditional web technologies such as HTML, CSS, and JavaScript, with **Thymeleaf** for rendering dynamic content and **H2 Database** for data management.

- Language: Java
- Frontend: HTML, CSS, JavaScript
- Backend: Spring Boot, Spring Boot DevTools, Lombok, Spring Web, Thymeleaf, Spring Data JPA
- Database: H2 Database
  
## Screenshots of the application

### Homepage
![Etusivu](./src/main/resources/public/images/front_page.png)

### Consumer products department
![Kulutustuotteet-osasto](./src/main/resources/public/images/kulutustuotteet_page.png)

### Coffee machines department
![Kahvilaitteet-osasto](./src/main/resources/public/images/kahvilaitteet_page.png)

### Orders and search
![Tilaukset ja haku](./src/main/resources/public/images/order-list_and_search.png)

### VIP customer registration
![VIP-asiakasrekisteröinti](./src/main/resources/public/images/vipasiakas_page.png)

### Login page
![Sisäänkirjautumissivu](./src/main/resources/public/images/sign-in_page.png)

### Product management
![Lisää tuote](./src/main/resources/public/images/add-product_page.png)

### Supplier management
![Lisää toimittaja](./src/main/resources/public/images/add-supplier_page.png)

### Manufacturer management
![Lisää valmistaja](./src/main/resources/public/images/add-producer_page.png)

### Department management
![Lisää osasto](./src/main/resources/public/images/add-department_page.png)

### VIP customer management
![VIP-asiakkaiden hallinta](./src/main/resources/public/images/list-of-vip-customers_page.png)
