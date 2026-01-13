# 🧾 POS Microservice System

A production-style **Point of Sale (POS)** backend built using **Ktor, Kotlin, PostgreSQL, Docker, Microservices, API Gateway, and Service Discovery**.

This project demonstrates how modern backend systems are designed in real companies.

---

## 🧩 Architecture

Client<br>
  &nbsp;&nbsp;&nbsp;↓<br>
API Gateway (Ktor) :8080<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓<br>
  &nbsp;&nbsp;&nbsp;/products ───────────▶ Product Service (Ktor) :8081 (10.0.0.5) ───▶ product-db<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓<br>
  &nbsp;&nbsp;&nbsp;/orders ───────────▶ Order Service (Docker DNS)<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Order Service #1 :8082 (10.0.0.11) ───▶ order-db<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Order Service #2 :8082 (10.0.0.12) ───▶ order-db<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Order Service #3 :8082 (10.0.0.13) ───▶ order-db<br>

Each service has:
- Its own database
- Its own Docker container
- HTTP communication via API Gateway

---

## 🛠️ Tech Stack

| Layer | Technology |
|------|-----------|
| Language | Kotlin |
| Backend Framework | Ktor |
| Databases | PostgreSQL |
| ORM | Exposed |
| HTTP Client | Ktor Client |
| API Gateway | Ktor |
| Service Discovery | Docker DNS |
| Containerization | Docker & Docker Compose |

---

## 🧱 Microservices

### 🟢 Product Service
- Create products
- Update stock
- List products
- Deduct inventory

### 🔵 Order Service
- Create orders
- Fetch orders
- Calls Product Service to validate stock
- Saves order history

### 🔴 API Gateway
- Single public entry point
- Routes `/products` → Product Service
- Routes `/orders` → Order Service
- Supports Service Discovery & Load Balancing

---

## 🚀 How to Run

### 1️⃣ Requirements
- Docker
- Docker Compose

---

### 2️⃣ Start the system

From the project root:

```bash
docker compose build --no-cache
docker compose up --scale order-service=3
