# 🚀 Railway Deployment Guide for EnsarkBank

This guide walks you through publishing the **EnsarkBank** platform (MySQL Database, Spring Boot Backend, and Angular Frontend) to **[Railway.app](https://railway.app/)**.

---

## 📋 System Architecture on Railway

```
                   ┌─────────────────────────────────────────┐
                   │           Railway Project               │
                   │                                         │
                   │  ┌──────────────┐    ┌───────────────┐  │
  Browser Client ──┼─►│ ensark-      │    │ ensark-       │  │
 (Angular Frontend)│  │ frontend     │───►│ backend       │  │
                   │  │ (Nginx)      │    │ (Spring Boot) │  │
                   │  └──────────────┘    └───────┬───────┘  │
                   │                              │          │
                   │                      ┌───────▼───────┐  │
                   │                      │ MySQL         │  │
                   │                      │ Database      │  │
                   │                      └───────────────┘  │
                   └─────────────────────────────────────────┘
```

---

## Step 1: Create a Railway Project & Add MySQL

1. Log into your [Railway Console](https://railway.app/dashboard).
2. Click **+ New Project** and select **Provision MySQL**.
3. Railway will create a MySQL database instance and automatically set the following environment variables:
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`
   - `MYSQLDATABASE`
   - `MYSQLURL`

---

## Step 2: Deploy Backend (`ensark`)

1. In your Railway Project, click **+ New** ➔ **GitHub Repo**.
2. Select your `BankManagementSystem` repository.
3. Click on the newly created service card ➔ go to **Settings**:
   - **Service Name**: `ensark-backend`
   - **Root Directory**: `ensark`
   - Railway will automatically detect `ensark/Dockerfile`!
4. Go to **Variables** tab and click **Add Reference** or add these variables:
   - `SPRING_DATASOURCE_URL`: `${{MySQL.MYSQLURL}}` *(or Railway will automatically inject `MYSQLURL`)*
   - `JWT_SECRET`: `YourSuperSecretKeyWithAtLeast32BytesLength!`
   - `FRONTEND_URL`: `https://<your-frontend-domain>.up.railway.app` *(update after Step 3)*
   - `UPLOAD_DIR`: `/app/uploads`
   - `SMTP_USERNAME`: `your-email@gmail.com` *(optional)*
   - `SMTP_PASSWORD`: `your-app-password` *(optional)*
5. Go to **Settings** ➔ **Networking** ➔ click **Generate Domain** (e.g. `ensark-backend.up.railway.app`).

---

## Step 3: Configure & Deploy Frontend (`ensark-frontend`)

1. Update `ensark-frontend/src/environments/environment.prod.ts` with your Railway Backend URL:

   ```typescript
   export const environment = {
     production: true,
     apiUrl: 'https://<your-backend-domain>.up.railway.app/api/',
     imageUrl: 'https://<your-backend-domain>.up.railway.app/images/',
     uploadsUrl: 'https://<your-backend-domain>.up.railway.app/uploads/',
     secretKey: 'EnSarkOrPiRoy@2026'
   };
   ```

2. Commit & push changes to GitHub:
   ```bash
   git add ensark-frontend/src/environments/environment.prod.ts
   git commit -m "Update prod API URL for Railway"
   git push origin main
   ```

3. In Railway, click **+ New** ➔ **GitHub Repo**.
4. Select `BankManagementSystem`.
5. Go to **Settings**:
   - **Service Name**: `ensark-frontend`
   - **Root Directory**: `ensark-frontend`
   - Railway will automatically build using `ensark-frontend/Dockerfile` and serve via Nginx on Railway's dynamic `$PORT`.
6. Go to **Settings** ➔ **Networking** ➔ click **Generate Domain** (e.g. `ensark-frontend.up.railway.app`).

---

## Step 4: Final Link Check & CORS Verification

1. Go back to `ensark-backend` service variables in Railway.
2. Ensure `FRONTEND_URL` matches your exact frontend URL (e.g. `https://ensark-frontend.up.railway.app`).
3. Open `https://ensark-frontend.up.railway.app` in your browser!

---

## 🐳 Local Docker Testing (Optional)

You can test all 3 containers locally using Docker Compose before deploying:

```bash
# Build and start all services (MySQL + Backend + Frontend)
docker-compose up --build

# Stop all services
docker-compose down
```

- **Frontend**: `http://localhost`
- **Backend API**: `http://localhost:8085/api/`
- **Swagger Docs**: `http://localhost:8085/swagger-ui.html`
- **MySQL DB**: `localhost:3306`
