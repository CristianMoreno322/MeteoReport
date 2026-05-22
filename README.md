# MeteoReport 🌤️

Aplicación Android para reportes meteorológicos en tiempo real.

## Estructura del proyecto
- `data/` — Room Database, DAOs, Retrofit
- `model/` — Entidades (WeatherReport, WeatherAlert)
- `repository/` — WeatherRepository
- `ui/` — Fragments y Adapters
- `viewmodel/` — WeatherViewModel

## Contrato de API — OpenWeatherMap

 ### Arquitectura
La app implementa el patrón **MVVM** (Model-View-ViewModel) con los siguientes componentes:
- **Room** para mantener de manera local los informes meteorológicos
- **Retrofit** para consumo de la API OpenWeatherMap
- **Navigation Component** para navegación entre fragments
- **LiveData** para observar cambios en la base de datos

### Flujo de la app
1. El usuario ingresa a `HomeFragment`
2. Navega a `ReportListFragment` donde ve todos los reportes guardados
3. Presiona "+ Nuevo Reporte" → navega a `NewReportFragment`
4. Ingresa ciudad y descripción → presiona "Guardar Reporte"
5. El `WeatherViewModel` llama al `WeatherRepository` que guarda en Room
6. La lista se actualiza automáticamente via `LiveData`

### Pantallas implementadas
| Pantalla | Descripción |
|---|---|
| Home | Pantalla principal de bienvenida |
| Lista de Reportes | Muestra todos los reportes guardados |
| Nuevo Reporte | Formulario para ingresar un reporte |
| Detalle de Reporte | Vista detallada de un reporte |
| Configuración | Ajustes de la aplicación |

### Respuesta de la API
```json
{
  "name": "Bucaramanga",
  "sys": { "country": "CO" },
  "main": { "temp": 24.5, "humidity": 78, "pressure": 1012 },
  "wind": { "speed": 3.2 },
  "weather": [{ "main": "Rain", "description": "fuerte lluvias", "icon": "10d" }],
  "dt": 1715789200
}
```

### Entidad guardada en Room
```json
{
  "id": 1,
  "city": "Bucaramanga",
  "country": "CO",
  "temperature": 24.5,
  "humidity": 78,
  "windSpeed": 3.2,
  "pressure": 1012,
  "weatherMain": "Rain",
  "description": "fuerte lluvias",
  "iconCode": "10d",
  "timestamp": 1715789200,
  "isSynced": false
}
```
## Entregable 2: Persistencia y lógica offline

### Cambios realizados

#### CRUD completo
- Agregado `updateReport()` y `deleteReport()` en WeatherDao, WeatherRepository y WeatherViewModel
- Formulario de creación con validaciones (ciudad, país, temperatura, humedad, descripción)
- Pantalla de detalle con edición y eliminación de reportes

#### Formularios
- `NewReportFragment` — campos: ciudad, país, temperatura, humedad, viento, presión, descripción
- `ReportDetailFragment` — edición de todos los campos + botón eliminar

#### Lista de reportes
- RecyclerView con `ReportAdapter` usando `ListAdapter` y `DiffUtil`
- Estado vacío cuando no hay reportes
- Click en item navega al detalle

#### UI mejorada
- Home rediseñado con header azul claro, tarjetas de estadísticas
- Contador de reportes totales, pendientes y sincronizados en tiempo real
- Fondo con tarjetas redondeadas (`card_background.xml`)

#### Navegación
- Argumento `reportId` agregado al nav_graph para pasar ID al detalle
- Acción `toNewReportFromHome` desde Home directo al formulario

### Funciona sin internet
Todos los datos se guardan en Room (SQLite local) sin necesidad de conexión.
