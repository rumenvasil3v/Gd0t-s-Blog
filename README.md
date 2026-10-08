# Gd0t's Space

My personal blog and portfolio, built from scratch with Spring Boot. Live at **[gd0t.uk](https://gd0t.uk/)**.

## Features

- **Blog with Markdown posts:** content is stored as Markdown and rendered to HTML when a post is viewed (CommonMark).
- **Latest posts first:** the home page lists posts newest to oldest.
- **Admin-only authoring:** form login protects creating, editing and deleting posts.
- **Resume and Projects pages:** a static portfolio alongside the blog.
- **Responsive layout:** Bootstrap 5 with a collapsible navbar for mobile.
- **Containerised:** multi-stage Dockerfile for a small runtime image.

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java (Docker image uses 21; `pom.xml` targets 17) |
| Framework | Spring Boot 4.0.6 (Web MVC, Data JPA, Security) |
| Templating | Thymeleaf with Spring Security extras |
| Database | PostgreSQL (H2 available for local use) |
| Markdown | CommonMark 0.21.0 |
| Styling | Bootstrap 5.3.2 plus custom CSS |
| Build | Maven (wrapper included) |
| Deployment | Docker |

## Routes

| Route | Access | Purpose |
| --- | --- | --- |
| `/` | Public | Blog home, latest posts first |
| `/post/{id}` | Public | Read a single post |
| `/resume` | Public | Resume |
| `/projects` | Public | Projects |
| `/login` | Public | Admin login |
| `/new` | Admin | Write a new post |
| `/post/{id}/edit` | Admin | Edit a post |
| `/post/{id}/delete` | Admin | Delete a post |

## Project Structure

```
Gd0t-s-Blog/
├── Dockerfile                  # Build from the repo root
└── gd0t/
    ├── pom.xml
    ├── Dockerfile              # Build from inside gd0t/
    └── src/main/
        ├── java/com/gd0t/gd0t/
        │   ├── Gd0tApplication.java
        │   ├── bootstrap/      # DataLoader: seeds sample posts on an empty DB
        │   ├── config/         # SecurityConfig: routes, login, admin user
        │   ├── controller/     # BlogController, PortfolioController
        │   ├── model/          # Post entity
        │   ├── repository/     # PostRepository (Spring Data JPA)
        │   └── service/        # PostService, MarkdownService
        └── resources/
            ├── application.properties
            ├── static/css/style.css
            └── templates/      # index, post, create-post, edit-post,
                                # resume, projects, fragments
```
