# Cambios para Deshabilitar Usuarios

## 📋 Resumen
Se implementó la funcionalidad para que el administrador pueda habilitar/deshabilitar usuarios desde el frontend.

---

## 🔧 Cambios en Backend (Java Spring Boot)

### 1. Modelo `Users.java`
**Ubicación:** `src/main/java/com/example/TiendaSuplementos/Model/Users.java`

**Cambios realizados:**
- ✅ Agregado campo `enabled` (Boolean, default: true)
- ✅ Agregados getters y setters para `enabled`

```java
@Column(name = "enabled")
private Boolean enabled = true;

public Boolean getEnabled() {
    return enabled;
}

public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
}
```

---

### 2. Servicio `UsersService.java`
**Ubicación:** `src/main/java/com/example/TiendaSuplementos/Service/UsersService.java`

**Cambios realizados:**
- ✅ Actualizado método `update()` para incluir el campo `enabled`
- ✅ Agregado método `toggleEnabled()` para cambiar el estado del usuario
- ✅ Actualizado método `login()` para verificar que el usuario esté habilitado

```java
// En el método update()
if (users.getEnabled() != null) {
    existing.setEnabled(users.getEnabled());
}

// Nuevo método
public Users toggleEnabled(Long id) {
    return repository.findById(id)
            .map(existing -> {
                existing.setEnabled(!existing.getEnabled());
                return repository.save(existing);
            })
            .orElseThrow(() -> new RuntimeException("User not found with id " + id));
}

// En el método login() - ahora valida que el usuario esté habilitado
public Optional<Users> login(String email, String password) {
    Users user = repository.findByEmail(email);
    if (user != null && user.getPassword().equals(password) && user.getEnabled()) {
        return Optional.of(user);
    }
    return Optional.empty();
}
```

---

### 3. Controlador `UsersController.java`
**Ubicación:** `src/main/java/com/example/TiendaSuplementos/Controller/UsersController.java`

**Cambios realizados:**
- ✅ Agregado endpoint `PATCH /api/users/{id}/toggle-enabled`

```java
@PatchMapping("/{id}/toggle-enabled")
public ResponseEntity<Users> toggleEnabled(@PathVariable Long id) {
    try {
        Users updated = service.toggleEnabled(id);
        return ResponseEntity.ok(updated);
    } catch (RuntimeException ex) {
        return ResponseEntity.notFound().build();
    }
}
```

---

### 4. Base de Datos (SQL)
**Ejecutar en Supabase SQL Editor:**

```sql
ALTER TABLE users ADD COLUMN enabled BOOLEAN DEFAULT true;
```

---

## 🎨 Implementación Frontend

### Endpoint del Backend
```
PATCH https://tienda-suplementos-backend.onrender.com/api/users/{id}/toggle-enabled
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "username": "usuario1",
  "email": "usuario1@example.com",
  "enabled": false,
  "role_id": 2,
  "setting_id": 1
}
```

---

## 📦 Código Frontend para Implementar

### Opción 1: React (JavaScript/TypeScript)

```jsx
// UserManagement.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const API_URL = 'https://tienda-suplementos-backend.onrender.com/api/users';

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(API_URL);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  const toggleUserEnabled = async (userId) => {
    try {
      await axios.patch(`${API_URL}/${userId}/toggle-enabled`);
      fetchUsers(); // Recargar lista
    } catch (error) {
      console.error('Error toggling user status:', error);
      alert('Error al cambiar el estado del usuario');
    }
  };

  return (
    <div className="user-management">
      <h2>Gestión de Usuarios</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Usuario</th>
            <th>Email</th>
            <th>Estado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>
                <span className={user.enabled ? 'badge-active' : 'badge-inactive'}>
                  {user.enabled ? 'Activo' : 'Deshabilitado'}
                </span>
              </td>
              <td>
                <button 
                  onClick={() => toggleUserEnabled(user.id)}
                  className={user.enabled ? 'btn-disable' : 'btn-enable'}
                >
                  {user.enabled ? 'Deshabilitar' : 'Habilitar'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagement;
```

---

### Opción 2: Vue.js

```vue
<template>
  <div class="user-management">
    <h2>Gestión de Usuarios</h2>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Usuario</th>
          <th>Email</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <span :class="user.enabled ? 'badge-active' : 'badge-inactive'">
              {{ user.enabled ? 'Activo' : 'Deshabilitado' }}
            </span>
          </td>
          <td>
            <button 
              @click="toggleUserEnabled(user.id)"
              :class="user.enabled ? 'btn-disable' : 'btn-enable'"
            >
              {{ user.enabled ? 'Deshabilitar' : 'Habilitar' }}
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserManagement',
  data() {
    return {
      users: [],
      API_URL: 'https://tienda-suplementos-backend.onrender.com/api/users'
    };
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get(this.API_URL);
        this.users = response.data;
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    async toggleUserEnabled(userId) {
      try {
        await axios.patch(`${this.API_URL}/${userId}/toggle-enabled`);
        this.fetchUsers();
      } catch (error) {
        console.error('Error toggling user status:', error);
        alert('Error al cambiar el estado del usuario');
      }
    }
  }
};
</script>

<style scoped>
/* Estilos incluidos abajo */
</style>
```

---

### Opción 3: Angular (TypeScript)

```typescript
// user-management.component.ts
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface User {
  id: number;
  username: string;
  email: string;
  enabled: boolean;
  role_id: number;
  setting_id: number;
}

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: User[] = [];
  private API_URL = 'https://tienda-suplementos-backend.onrender.com/api/users';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchUsers();
  }

  fetchUsers(): void {
    this.http.get<User[]>(this.API_URL).subscribe({
      next: (data) => this.users = data,
      error: (error) => console.error('Error fetching users:', error)
    });
  }

  toggleUserEnabled(userId: number): void {
    this.http.patch(`${this.API_URL}/${userId}/toggle-enabled`, {}).subscribe({
      next: () => this.fetchUsers(),
      error: (error) => {
        console.error('Error toggling user status:', error);
        alert('Error al cambiar el estado del usuario');
      }
    });
  }
}
```

```html
<!-- user-management.component.html -->
<div class="user-management">
  <h2>Gestión de Usuarios</h2>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Usuario</th>
        <th>Email</th>
        <th>Estado</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr *ngFor="let user of users">
        <td>{{ user.id }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>
          <span [class]="user.enabled ? 'badge-active' : 'badge-inactive'">
            {{ user.enabled ? 'Activo' : 'Deshabilitado' }}
          </span>
        </td>
        <td>
          <button 
            (click)="toggleUserEnabled(user.id)"
            [class]="user.enabled ? 'btn-disable' : 'btn-enable'"
          >
            {{ user.enabled ? 'Deshabilitar' : 'Habilitar' }}
          </button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
```

---

## 🎨 CSS para todos los frameworks

```css
.user-management {
  padding: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
  font-weight: bold;
  color: #333;
}

tbody tr:hover {
  background-color: #f5f5f5;
}

.badge-active {
  background-color: #4CAF50;
  color: white;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.85em;
  font-weight: 500;
}

.badge-inactive {
  background-color: #f44336;
  color: white;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.85em;
  font-weight: 500;
}

.btn-disable, .btn-enable {
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-disable {
  background-color: #f44336;
  color: white;
}

.btn-enable {
  background-color: #4CAF50;
  color: white;
}

.btn-disable:hover, .btn-enable:hover {
  opacity: 0.8;
  transform: translateY(-1px);
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.btn-disable:active, .btn-enable:active {
  transform: translateY(0);
}
```

---

## 🔒 Seguridad Importante

### 1. Proteger el endpoint (solo admins)
En tu frontend, asegúrate de:
- Verificar que el usuario tenga rol de administrador antes de mostrar la interfaz
- Incluir el token JWT en las peticiones

```javascript
// Ejemplo con axios
const config = {
  headers: { 
    'Authorization': `Bearer ${token}` 
  }
};

await axios.patch(`${API_URL}/${userId}/toggle-enabled`, {}, config);
```

### 2. Validación en Backend (recomendado)
Agrega validación de roles en el controlador:

```java
@PatchMapping("/{id}/toggle-enabled")
@PreAuthorize("hasRole('ADMIN')") // Requiere Spring Security
public ResponseEntity<Users> toggleEnabled(@PathVariable Long id) {
    // ... código existente
}
```

---

## ✅ Checklist de Implementación

### Backend
- [x] Agregar campo `enabled` al modelo Users
- [x] Actualizar UsersService con método `toggleEnabled`
- [x] Agregar endpoint en UsersController
- [x] Actualizar método `login` para verificar `enabled`
- [ ] Ejecutar SQL en Supabase
- [ ] Probar endpoint con Postman/Thunder Client

### Frontend
- [ ] Crear componente de gestión de usuarios
- [ ] Implementar llamada al endpoint `toggle-enabled`
- [ ] Agregar estilos CSS
- [ ] Validar permisos de administrador
- [ ] Incluir token JWT en las peticiones
- [ ] Probar funcionalidad completa

---

## 🧪 Pruebas con Postman/Thunder Client

### 1. Listar usuarios
```
GET https://tienda-suplementos-backend.onrender.com/api/users
```

### 2. Deshabilitar/Habilitar usuario
```
PATCH https://tienda-suplementos-backend.onrender.com/api/users/1/toggle-enabled
Headers:
  Content-Type: application/json
  Authorization: Bearer {tu-token-jwt}
```

### 3. Intentar login con usuario deshabilitado
```
POST https://tienda-suplementos-backend.onrender.com/api/auth/login
Body:
{
  "email": "usuario@example.com",
  "password": "password123"
}
```
Debería fallar si el usuario está deshabilitado.

---

## 📞 Notas Adicionales

- Los usuarios deshabilitados **no podrán iniciar sesión**
- El campo `enabled` es de tipo Boolean (true/false)
- Por defecto, todos los usuarios nuevos estarán habilitados
- El endpoint `toggle-enabled` cambia automáticamente entre true/false
- Se recomienda agregar logs de auditoría para rastrear quién deshabilita usuarios

---

## 🚀 Despliegue

Después de implementar los cambios:

1. Ejecuta el SQL en Supabase
2. Reinicia tu aplicación Spring Boot
3. Verifica que el endpoint funcione
4. Implementa el componente frontend
5. Prueba la funcionalidad completa

---

**Fecha de implementación:** 21 de noviembre de 2025
**Autor:** Backend changes implemented
