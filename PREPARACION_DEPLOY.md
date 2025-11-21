# 🛠️ Preparación Local para Despliegue en Render

Antes de desplegar a Render, verifica que todo funcione correctamente en local.

---

## ✅ Checklist Pre-Despliegue

### 1. Probar Build de Docker Local

```powershell
# Construir imagen
docker build -t tienda-suplementos:test .

# Ejecutar contenedor (con tus variables de entorno)
docker run -p 8080:8080 `
  -e DB_URL="jdbc:postgresql://tu-db/nombre" `
  -e DB_USERNAME="tu_usuario" `
  -e DB_PASSWORD="tu_password" `
  -e STRIPE_SECRET_KEY="sk_test_xxxx" `
  -e STRIPE_PUBLISHABLE_KEY="pk_test_xxxx" `
  tienda-suplementos:test

# Probar que funciona
curl http://localhost:8080/actuator/health
```

### 2. Probar con Perfil de Producción Local

```powershell
# Usando Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=production

# O usando el JAR compilado
./mvnw clean package -DskipTests
java -Dspring.profiles.active=production -jar target/TiendaSuplementos-0.0.1-SNAPSHOT.jar
```

### 3. Verificar Endpoints

Abre tu navegador o usa curl:

```powershell
# Health check
curl http://localhost:8080/actuator/health

# Swagger UI
# http://localhost:8080/swagger-ui/index.html

# API Docs
curl http://localhost:8080/v3/api-docs

# Página de inicio
# http://localhost:8080
```

### 4. Revisar Logs

Busca errores o warnings:
```
[INFO] Started TiendaSuplementosApplication in X seconds
```

---

## 🚀 Subir a GitHub

```bash
# Agregar todos los archivos nuevos
git add .

# Commit con mensaje descriptivo
git commit -m "Add Render deployment configuration"

# Push a GitHub
git push origin main
```

---

## 🌐 Desplegar en Render

### Opción A: Blueprint (Automático)

1. Ve a [Render Dashboard](https://dashboard.render.com)
2. Click **"New +"** → **"Blueprint"**
3. Conecta tu repositorio GitHub
4. Render detectará `render.yaml`
5. Click **"Apply"**

### Opción B: Manual

1. Sigue las instrucciones en `DESPLIEGUE_RENDER.md`

---

## 🔧 Configurar Variables de Entorno en Render

En el dashboard de tu servicio, ve a **"Environment"** y agrega:

```env
# Base de datos (si usas Render PostgreSQL, esto se auto-configura)
DB_URL=jdbc:postgresql://dpg-xxxxx.oregon-postgres.render.com/tienda_suplementos
DB_USERNAME=tienda_suplementos_user
DB_PASSWORD=xxxxxxxxxxxxx

# Spring
SPRING_PROFILES_ACTIVE=production

# Stripe (obtén estas keys de tu dashboard de Stripe)
STRIPE_SECRET_KEY=sk_live_xxxxx
STRIPE_PUBLISHABLE_KEY=pk_live_xxxxx

# Opcional - CORS (cambia por la URL de tu frontend)
CORS_ALLOWED_ORIGINS=https://tu-frontend.com
```

---

## 📊 Monitorear el Despliegue

### En Render Dashboard:

1. **Logs Tab:** Ver progreso del build
2. **Events Tab:** Historial de deployments
3. **Metrics Tab:** CPU, Memoria, Requests

### Primera vez (toma ~5-10 minutos):
```
Building Docker image...
[Building] Step 1/15 : FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
[Building] Step 2/15 : WORKDIR /app
...
[Deploying] Service is live 🎉
```

---

## 🧪 Probar tu API en Producción

Una vez desplegado, reemplaza la URL:

```bash
# Health check
curl https://tu-app.onrender.com/actuator/health

# Debería retornar:
{
  "status": "UP"
}
```

```bash
# Swagger UI
# https://tu-app.onrender.com/swagger-ui/index.html

# Página de inicio
# https://tu-app.onrender.com
```

---

## 🐛 Si algo sale mal...

### Build falla:

1. Revisa logs en Render Dashboard
2. Verifica que Java 21 esté especificado en `pom.xml`
3. Asegúrate que el `Dockerfile` esté en la raíz

### Base de datos no conecta:

1. Verifica variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
2. Formato JDBC correcto: `jdbc:postgresql://host:5432/db`
3. Si usas Supabase, agrega `?sslmode=require` al final

### Health check falla:

1. Verifica que Actuator esté en `pom.xml`
2. Endpoint correcto: `/actuator/health`
3. Revisa logs para ver si la app inició correctamente

### Timeout en primera request:

- Normal en plan Free (spin down después de 15 min)
- Espera 30-50 segundos en la primera request
- Considera upgrade a plan Starter ($7/mes)

---

## 💡 Tips Finales

### Optimización de Costos:
- Usa plan Free para desarrollo/staging
- Upgrade a Starter solo para producción

### Seguridad:
- Usa `sk_live_` keys de Stripe solo en producción
- Configura CORS para permitir solo tu dominio frontend
- No compartas variables de entorno en GitHub

### Performance:
- Region: Elige la más cercana a tus usuarios
- Database: Misma region que el backend
- Caching: Considera Redis si tienes muchas requests

---

## 📱 URLs Finales

Después del despliegue exitoso:

| Recurso | URL |
|---------|-----|
| API Base | `https://tu-app.onrender.com` |
| Swagger UI | `https://tu-app.onrender.com/swagger-ui/index.html` |
| Health | `https://tu-app.onrender.com/actuator/health` |
| API Docs | `https://tu-app.onrender.com/v3/api-docs` |

---

## ✅ Checklist Final

- [ ] Build Docker local exitoso
- [ ] Tests pasando (`./mvnw test`)
- [ ] Health check respondiendo
- [ ] Código en GitHub (rama `main`)
- [ ] Servicio creado en Render
- [ ] Variables de entorno configuradas
- [ ] Base de datos conectada
- [ ] Primera request exitosa
- [ ] Swagger UI accesible
- [ ] Frontend apuntando a nueva URL

---

¡Tu backend está listo para producción! 🎉

Para más detalles, consulta `DESPLIEGUE_RENDER.md`
