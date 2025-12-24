## Mini e-learning platform (Servlet/JSP/JDBC)

This project is a **mini e-learning platform J2EE** based on:

- **Stack**: Servlets, JSP, JDBC, in-memory H2 database
- **Build**: Maven multi-module (`server` + `webapp`)
- **Deployment**: `webapp.war` on an **Apache Tomcat** application server (Tomcat 10+)

Main features:

- **Basic authentication** (HTTP sessions)
- **Role management**: `ADMIN` and `STUDENT`
- **PDF course upload** (ADMIN role)
- **Module-based browsing**
- **Comments** on resources

This guide targets **beginners on Ubuntu/Linux** and explains how to install everything and run the project **locally**.

---

## 0. Quick start (copy/paste on Ubuntu)

These commands assume:

- You are on **Ubuntu** (or Debian-like).
- You want to run the app **locally**.
- You will clone the project into `~/projet_j2ee_e-learning`.

```bash
# 0) Go to your home directory
cd ~

# 1) Install system dependencies (Java 17, Maven, Git, Tomcat 10)
sudo apt update
sudo apt install -y openjdk-17-jdk maven git tomcat10 tomcat10-admin

# 2) Clone the project
git clone https://github.com/ngrassa/projet_j2ee_e-learning.git
cd projet_j2ee_e-learning

# 3) Build only the webapp module (generates webapp/target/webapp.war)
mvn -pl webapp clean package -DskipTests

# 4) Deploy the WAR to Tomcat (installed via apt)
sudo cp webapp/target/webapp.war /var/lib/tomcat10/webapps/
sudo systemctl restart tomcat10

# 5) Open the application in your browser:
# URL: http://localhost:8080/webapp/
# (automatic redirect to /login)
```

Test accounts:

- Admin: `admin` / `admin123`
- Student: `student` / `student123`

The following sections explain each step in more detail.

---

## 1. Environment and prerequisites

### 1.1. Operating system

- Target OS: **Linux / Ubuntu** (or Debian-based distributions).
- All commands below are standard shell commands (`bash`) and standard Ubuntu packages.

### 1.2. Java (JDK 17)

Check if Java is installed:

```bash
java -version
```

If the command is missing or the version is lower than 17, install OpenJDK 17:

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
```

Verify:

```bash
java -version
# Expected: something like "openjdk version \"17.x...\""
```

### 1.3. Maven

Maven is used to manage dependencies and build the project.

Check Maven:

```bash
mvn -version
```

If the command is not found:

```bash
sudo apt update
sudo apt install -y maven
```

### 1.4. Git

Git is used to clone the repository.

Check Git:

```bash
git --version
```

If missing:

```bash
sudo apt update
sudo apt install -y git
```

### 1.5. Apache Tomcat 10

The `webapp` module produces a **WAR** compatible with **Tomcat 10+** (Jakarta EE 9+).

Install Tomcat 10 via apt:

```bash
sudo apt update
sudo apt install -y tomcat10 tomcat10-admin
```

Useful paths (default Ubuntu installation):

- Webapps directory (where WAR files are deployed):  
  `/var/lib/tomcat10/webapps`
- Configuration file (if needed):  
  `/etc/tomcat10/server.xml`
- Logs directory:  
  `/var/log/tomcat10/`

Check that Tomcat is running:

```bash
systemctl status tomcat10
```

If it is not `active (running)`, start it:

```bash
sudo systemctl start tomcat10
```

---

## 2. Get the project locally

From a terminal:

```bash
cd ~
git clone https://github.com/ngrassa/projet_j2ee_e-learning.git
cd projet_j2ee_e-learning
```

Simplified structure:

- `pom.xml` – parent POM (multi-module)
- `server/` – simple Java module (example, not required to run the webapp)
- `webapp/` – **web module** that produces `webapp.war`
  - `src/main/webapp/` – JSPs, `web.xml`, static resources
  - `src/main/java/com/example/elearning/...` – servlets, filters, DAOs, models, DB config

---

## 3. Build the project and generate the WAR

Make sure you are at the root of the project (`projet_j2ee_e-learning`):

```bash
cd ~/projet_j2ee_e-learning
```

### 3.1. Build all modules (optional)

To build both `server` and `webapp`:

```bash
mvn clean install
```

This command:

- Downloads all required dependencies (Servlet/JSP, H2, JSTL, Spring Web, etc.).
- Compiles the `server` and `webapp` modules.
- Generates a **WAR** in `webapp/target/webapp.war`.

If tests in the `server` module cause issues, you can skip tests:

```bash
mvn clean install -DskipTests
```

### 3.2. Build only the webapp module (recommended for running locally)

To focus only on the deployable web module:

```bash
mvn -pl webapp clean package -DskipTests
```

At the end of the build, verify that the following file exists:

```bash
ls webapp/target
# Expected: webapp.war
```

If the build fails, inspect the error message in the terminal. You can copy it and use it to debug or ask for help.

---

## 4. Deploy the application on Tomcat

### 4.1. Deploy to Tomcat installed via apt (Ubuntu default)

Assuming Tomcat 10 was installed with `apt` and uses the default paths:

```bash
cd ~/projet_j2ee_e-learning
sudo cp webapp/target/webapp.war /var/lib/tomcat10/webapps/
```

Tomcat will automatically deploy the WAR and create a `webapp/` directory under `webapps`.

To ensure a clean redeploy, restart Tomcat:

```bash
sudo systemctl restart tomcat10
```

### 4.2. Deploy to a manually downloaded Tomcat (alternative)

If instead of using `apt` you downloaded Tomcat manually and unpacked it into `~/tomcat10`, you can deploy like this:

```bash
cd ~/projet_j2ee_e-learning
cp webapp/target/webapp.war ~/tomcat10/webapps/
```

Then restart Tomcat:

```bash
cd ~/tomcat10/bin
./shutdown.sh || true
./startup.sh
```

In this case, the `webapps/` directory is inside your Tomcat installation directory.

---

## 5. Run and use the application

### 5.1. Access the application

By default, Tomcat listens on:

```text
http://localhost:8080
```

The application is deployed under the **context path `webapp`** (the name of the WAR).

Open your browser and go to:

```text
http://localhost:8080/webapp/
```

`index.jsp` will redirect you to the login page:

```text
http://localhost:8080/webapp/login
```

### 5.2. Demo accounts and roles

The H2 in-memory database is initialized automatically at application startup by `DatabaseInitializer`. It creates demo users and modules.

Available users:

- **Admin**
  - Username: `admin`
  - Password: `admin123`
  - Role: `ADMIN`
- **Student**
  - Username: `student`
  - Password: `student123`
  - Role: `STUDENT`

### 5.3. Example usage scenario

1. Open `http://localhost:8080/webapp/`.
2. Log in as **admin**:
   - Username: `admin`
   - Password: `admin123`
3. You arrive on the **dashboard**:
   - List of modules (e.g. *Java Web*, *DevOps*).
4. In the navigation menu, click **Depot PDF**:
   - Select a module.
   - Enter a **title** for the course.
   - Choose a **PDF file** from your machine.
   - Submit → the file is stored on the server and linked to the module.
5. Go back to the **dashboard**, click on a module:
   - You see the list of **PDF resources**.
   - Each resource has a link to **open the PDF** and a **Comments** section.
6. Add a comment:
   - Type some text and submit → the comment is stored in the H2 database.
7. Log out using the **Déconnexion** button.
8. Log in as **student**:
   - Username: `student`
   - Password: `student123`
   - You can **view modules, open PDFs, and comment**, but you cannot upload new PDFs (restricted to `ADMIN`).

---

## 6. Technical overview (optional)

For a quick understanding of the code:

- `webapp/src/main/webapp/WEB-INF/web.xml`  
  Jakarta EE configuration (servlets, auth filter, DB initializer).
- `webapp/src/main/java/com/example/elearning/config/DatabaseInitializer.java`  
  Creates H2 tables (`users`, `modules`, `resources`, `comments`) and inserts demo data.
- `webapp/src/main/java/com/example/elearning/config/DataSourceFactory.java`  
  Provides JDBC connections to the in-memory H2 database.
- `webapp/src/main/java/com/example/elearning/security/AuthFilter.java`  
  Basic security filter (redirects to `/login` if not authenticated, restricts `/upload` to `ADMIN`).
- `webapp/src/main/java/com/example/elearning/web/*.java`  
  Servlets: `LoginServlet`, `LogoutServlet`, `DashboardServlet`, `ModuleServlet`, `UploadServlet`, `CommentServlet`.
- `webapp/src/main/java/com/example/elearning/repository/*.java`  
  DAOs for users, modules, resources, comments.
- `webapp/src/main/java/com/example/elearning/model/*.java`  
  Simple POJOs: `User`, `CourseModule`, `Resource`, `Comment`.
- `webapp/src/main/webapp/WEB-INF/views/*.jsp`  
  JSP views with a simple design (PicoCSS): login, dashboard, module, upload.

---

## 7. Troubleshooting

### 7.1. `mvn: command not found`

Maven is not installed.

```bash
sudo apt update
sudo apt install -y maven
```

### 7.2. `java: command not found` or wrong Java version

Java is missing or not Java 17.

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
java -version
```

### 7.3. Tomcat does not start or is not reachable

Check status:

```bash
systemctl status tomcat10
```

Start or restart:

```bash
sudo systemctl start tomcat10
sudo systemctl restart tomcat10
```

Check logs (Ubuntu default):

```bash
ls /var/log/tomcat10/
```

### 7.4. HTTP 404 on `/webapp`

- Check that `webapp.war` is present in the `webapps` directory:

  ```bash
  ls /var/lib/tomcat10/webapps
  ```

- Check that Tomcat has unpacked it into a `webapp/` directory.
- If not, redeploy and restart Tomcat:

  ```bash
  cd ~/projet_j2ee_e-learning
  sudo cp webapp/target/webapp.war /var/lib/tomcat10/webapps/
  sudo systemctl restart tomcat10
  ```

### 7.5. JSP or JSTL errors

- Make sure you rebuilt the WAR after any change to `pom.xml` or JSPs:

  ```bash
  cd ~/projet_j2ee_e-learning
  mvn -pl webapp clean package -DskipTests
  sudo cp webapp/target/webapp.war /var/lib/tomcat10/webapps/
  sudo systemctl restart tomcat10
  ```

---

## 8. Summary of commands to install and run

```bash
# 1) Install dependencies (Ubuntu)
sudo apt update
sudo apt install -y openjdk-17-jdk maven git tomcat10 tomcat10-admin

# 2) Clone the project
cd ~
git clone https://github.com/ngrassa/projet_j2ee_e-learning.git
cd projet_j2ee_e-learning

# 3) Build the webapp
mvn -pl webapp clean package -DskipTests

# 4) Deploy to Tomcat (apt installation)
sudo cp webapp/target/webapp.war /var/lib/tomcat10/webapps/
sudo systemctl restart tomcat10

# 5) Use in browser
# URL: http://localhost:8080/webapp/
# Login: admin/admin123 or student/student123
```
