# 🚀 Guía de Despliegue en Render

Esta guía te ayudará a desplegar tu aplicación Spring Boot en Render de forma óptima.

---

## 📋 Requisitos Previos

- ✅ Cuenta en [Render](https://render.com) (gratis)
- ✅ Repositorio Git (GitHub, GitLab o Bitbucket)
- ✅ Base de datos PostgreSQL (Supabase o Render)
- ✅ Claves de Stripe (si usas pagos)

---

## 🛠️ Archivos Creados

Se han creado los siguientes archivos para optimizar el despliegue:

### 1. `Dockerfile`
- **Multi-stage build** para reducir el tamaño de la imagen
- **Usuario no-root** para mayor seguridad
- **Health check** integrado
- **Optimizaciones JVM** para mejor rendimiento
- Imagen final: ~200MB (vs ~800MB sin optimización)

### 2. `.dockerignore`
- Excluye archivos innecesarios del build
- Reduce tiempo de compilación
- Mejora seguridad

### 3. `render.yaml`
- Configuración Infrastructure as Code
- Define servicios y base de datos
- Variables de entorno pre-configuradas

### 4. `application-production.properties`
- Configuración optimizada para producción
- Connection pooling configurado
- Logging apropiado
- Compresión HTTP habilitada

### 5. `.github/workflows/deploy-render.yml`
- CI/CD automático con GitHub Actions
- Despliegue al hacer push a `main`

---

## 🚀 Pasos de Despliegue

### Opción 1: Despliegue con `render.yaml` (Recomendado)

#### 1. Subir código a GitHub

```bash
git add .
git commit -m "Add Render deployment configuration"
git push origin main
```

#### 2. Crear servicio en Render

1. Ve a [Render Dashboard](https://dashboard.render.com)
2. Click en **"New +"** → **"Blueprint"**
3. Conecta tu repositorio de GitHub
4. Render detectará automáticamente el `render.yaml`
5. Click en **"Apply"**

#### 3. Configurar variables de entorno sensibles

En el dashboard de Render, ve a tu servicio y agrega:

```env
STRIPE_SECRET_KEY=sk_test_xxxxx
STRIPE_PUBLISHABLE_KEY=pk_test_xxxxx
```

#### 4. Esperar el despliegue

- Render construirá automáticamente la imagen Docker
- El proceso toma ~5-10 minutos la primera vez
- Deployments posteriores son más rápidos (~3-5 min)

---

### Opción 2: Despliegue Manual

#### 1. Crear Base de Datos PostgreSQL

1. En Render Dashboard, click **"New +"** → **"PostgreSQL"**
2. Nombre: `tienda-suplementos-db`
3. Database Name: `tienda_suplementos`
4. Region: `Oregon` (o tu región preferida)
5. Plan: **Free** (para pruebas)
6. Click **"Create Database"**

#### 2. Crear Web Service

1. Click **"New +"** → **"Web Service"**
2. Conecta tu repositorio
3. Configuración:
   - **Name:** `tienda-suplementos-backend`
   - **Region:** `Oregon` (misma que la DB)
   - **Branch:** `main`
   - **Runtime:** `Docker`
   - **Plan:** `Free` (para pruebas)

#### 3. Variables de Entorno

En la sección "Environment", agrega:

```env
# Base de datos (copia de la sección "Connect" de tu PostgreSQL)
DB_URL=jdbc:postgresql://dpg-xxxxx.oregon-postgres.render.com/tienda_suplementos
DB_USERNAME=tienda_suplementos_user
DB_PASSWORD=xxxxxxxxxxxxx

# Spring
SPRING_PROFILES_ACTIVE=production

# Stripe
STRIPE_SECRET_KEY=sk_test_xxxxx
STRIPE_PUBLISHABLE_KEY=pk_test_xxxxx

# Opcional - Límites de memoria
JAVA_OPTS=-Xmx512m -Xms256m
```

#### 4. Health Check

- **Health Check Path:** `/actuator/health`

#### 5. Desplegar

Click en **"Create Web Service"**

---

## ⚙️ Configuración de Base de Datos

### Opción A: Usar Render PostgreSQL (Recomendado para producción)

**Ventajas:**
- ✅ Misma plataforma, mejor latencia
- ✅ Backups automáticos
- ✅ Fácil conexión entre servicios

**Plan Free:**
- 90 días gratis
- Luego $7/mes

### Opción B: Usar Supabase (Tu configuración actual)

Si ya tienes Supabase configurado:

1. Obtén tu connection string de Supabase
2. Convierte al formato JDBC:
   ```
   jdbc:postgresql://db.xxx.supabase.co:5432/postgres?sslmode=require
   ```
3. Agrega las variables en Render:
   ```env
   DB_URL=jdbc:postgresql://db.xxx.supabase.co:5432/postgres?sslmode=require
   DB_USERNAME=postgres
   DB_PASSWORD=tu_password
   ```

---

## 🔧 Optimizaciones Aplicadas

### 1. Docker Multi-stage Build
```dockerfile
# Build stage - Maven con todas las dependencias
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Production stage - Solo JRE (más ligero)
FROM eclipse-temurin:21-jre-alpine
```
**Resultado:** Imagen de ~200MB vs ~800MB

### 2. JVM Optimizations
```bash
-XX:+UseContainerSupport    # Detecta límites del contenedor
-XX:MaxRAMPercentage=75.0   # Usa 75% de RAM disponible
-XX:+UseG1GC                # Garbage collector eficiente
```

### 3. Connection Pooling (HikariCP)
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

### 4. HTTP Compression
```properties
server.compression.enabled=true
```
**Resultado:** Respuestas JSON ~60-80% más pequeñas

### 5. Caching de Dependencias
Maven descarga dependencias solo cuando cambia `pom.xml`

---

## 📊 Monitoreo y Logs

### Ver Logs en Tiempo Real

1. En Render Dashboard → Tu servicio
2. Tab **"Logs"**
3. Verás logs en tiempo real

### Health Check

Tu aplicación expone:
- **Health:** `https://tu-app.onrender.com/actuator/health`
- **Info:** `https://tu-app.onrender.com/actuator/info`

### Métricas

```bash
curl https://tu-app.onrender.com/actuator/metrics
```

---

## 🔒 Seguridad

### Aplicadas:

✅ Usuario no-root en container
✅ Secrets en variables de entorno
✅ HTTPS automático (por Render)
✅ Error messages ocultos en producción
✅ Connection pooling con timeouts

### Recomendaciones adicionales:

1. **Configura CORS** apropiadamente:
   ```properties
   cors.allowed.origins=https://tu-frontend.com
   ```

2. **Rate Limiting** (agregar al proyecto):
   ```xml
   <dependency>
       <groupId>com.bucket4j</groupId>
       <artifactId>bucket4j-core</artifactId>
   </dependency>
   ```

3. **Spring Security** (si no está implementado):
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   ```

---

## 🆓 Plan Free de Render

### Limitaciones:
- ⏰ **Spin down:** Después de 15 min de inactividad
- 🐌 **Primera request:** Puede tardar 30-50 segundos
- 💾 **RAM:** 512 MB
- 🔄 **Builds:** 750 horas/mes

### Para evitar spin down:

**Opción 1:** Upgrade a plan Starter ($7/mes)

**Opción 2:** Usar un servicio de ping (gratis)
- [Uptime Robot](https://uptimerobot.com)
- [Cron-job.org](https://cron-job.org)
- Configurar ping cada 10 minutos a `/actuator/health`

---

## 🚨 Troubleshooting

### Problema: Build falla

**Solución 1:** Verifica Java 21 en `pom.xml`
```xml
<java.version>21</java.version>
```

**Solución 2:** Verifica que el Dockerfile esté en la raíz

### Problema: No se conecta a la base de datos

**Solución:** Verifica variables de entorno
```bash
# En Render Dashboard → Environment
DB_URL=jdbc:postgresql://...
DB_USERNAME=...
DB_PASSWORD=...
```

### Problema: Health check falla

**Solución:** Agrega Spring Boot Actuator al `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Problema: OutOfMemoryError

**Solución:** Ajusta `JAVA_OPTS`:
```env
JAVA_OPTS=-Xmx400m -Xms200m
```

---

## 📱 URLs de tu Aplicación

Después del despliegue, tendrás:

- **API Base:** `https://tienda-suplementos-backend.onrender.com`
- **Swagger UI:** `https://tienda-suplementos-backend.onrender.com/swagger-ui/index.html`
- **Health Check:** `https://tienda-suplementos-backend.onrender.com/actuator/health`
- **API Docs JSON:** `https://tienda-suplementos-backend.onrender.com/v3/api-docs`

---

## 🔄 CI/CD con GitHub Actions

### Configurar Deploy Hook

1. En Render → Tu servicio → Settings
2. Busca **"Deploy Hook"**
3. Copia la URL
4. En GitHub → Settings → Secrets
5. Crea secret: `RENDER_DEPLOY_HOOK_URL`
6. Pega la URL

Ahora cada push a `main` desplegará automáticamente.

---

## 💰 Costos Estimados

### Plan Free (Desarrollo):
- Backend: **$0**
- PostgreSQL: **$0** (90 días)
- **Total:** $0/mes (luego $7/mes por DB)

### Plan Starter (Producción):
- Backend: **$7/mes**
- PostgreSQL: **$7/mes**
- **Total:** $14/mes

### Plan Professional (Alta demanda):
- Backend: **$25/mes** (1GB RAM)
- PostgreSQL: **$25/mes** (1GB RAM)
- **Total:** $50/mes

---

## ✅ Checklist de Despliegue

- [ ] Código en GitHub
- [ ] Variables de entorno configuradas
- [ ] Base de datos creada y conectada
- [ ] Health check funcionando
- [ ] Swagger UI accesible
- [ ] CORS configurado para frontend
- [ ] Claves de Stripe en producción
- [ ] Logs revisados sin errores
- [ ] Primera request probada exitosamente
- [ ] Documentación actualizada con URLs

---

## 🎉 ¡Listo!

Tu backend está optimizado y listo para producción en Render.

**Próximos pasos:**
1. Conectar tu frontend a la nueva URL
2. Configurar dominio personalizado (opcional)
3. Monitorear logs y métricas
4. Implementar backups de base de datos

---

## 📚 Recursos Adicionales

- [Render Documentation](https://render.com/docs)
- [Spring Boot on Render](https://render.com/docs/deploy-spring-boot)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [Spring Boot Production Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html)

---

**¿Necesitas ayuda?** 
- [Render Community](https://community.render.com)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/render.com)
