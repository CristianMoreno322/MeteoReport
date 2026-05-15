# MeteoReport 🌤️

Aplicación Android para reportes meteorológicos en tiempo real.

## Estructura del proyecto
- `data/` — Room Database, DAOs, Retrofit
- `model/` — Entidades (WeatherReport, WeatherAlert)
- `repository/` — WeatherRepository
- `ui/` — Fragments y Adapters
- `viewmodel/` — WeatherViewModel

## Contrato de API — OpenWeatherMap

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
