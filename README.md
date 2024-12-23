# Socks management system

## Описание проекта
REST API для учета носков на складе магазина. Позволяет регистрировать поступления и отпуски носков, обновлять их данные, получать информацию с фильтрацией и загружать партии носков из файлов.

## Стек технологий
- **Java 17**  
- **Spring Boot 2.7**  
- **PostgreSQL**  
- **Gradle**  
- **Swagger/OpenAPI** (для документации)  
- **SLF4J** (для логирования)  

## Функциональность

### 1. Регистрация прихода носков
**POST** `/api/socks/income`  
**Параметры (SockDto):**  
- `color` – цвет носков  
- `cottonPart` – процентное содержание хлопка  
- `quantity` – количество  
**Описание:** Увеличивает количество носков на складе. Если носки с такими параметрами отсутствуют, создается новая запись.  

---  

### 2. Регистрация отпуска носков
**POST** `/api/socks/outcome`  
**Параметры (SockDto):**  
- `color` – цвет носков  
- `cottonPart` – процентное содержание хлопка  
- `quantity` – количество  
**Описание:** Уменьшает количество носков на складе, если их хватает. Если носков недостаточно, возвращается ошибка.  

---  

### 3. Получение количества носков с фильтрацией
**GET** `/api/socks`  
**Фильтры:**  
- `color` – цвет носков  
- `operator` – оператор сравнения (`moreThan`, `lessThan`, `equal`)  
- `cottonPart` – процент хлопка  
**Описание:** Возвращает список носков, соответствующих критериям фильтрации.  

---  

### 4. Обновление данных носков
**PUT** `/api/socks/{id}`  
**Параметры (SockDto):**  
- `color` – новый цвет носков  
- `cottonPart` – новый процент хлопка  
- `quantity` – новое количество  
**Описание:** Позволяет изменить параметры носков по ID.  

---  

### 5. Загрузка партий носков из файла
**POST** `/api/socks/batch`  
**Параметры:**  
- Файл в формате **Excel (.xls)**  
**Описание:** Принимает файл с партиями носков, содержащими цвет, процент хлопка и количество, и добавляет данные в базу.  

---  

## Дополнительные требования

### Логирование
- Все операции (приход, отпуск, обновление, запросы) логируются с помощью **SLF4J**.  

### Фильтрация и сортировка
- Фильтрация по диапазону процентного содержания хлопка (например, от 30 до 70%).  
- Сортировка результатов по цвету или проценту хлопка.  

### Улучшенная обработка ошибок
- Централизованная обработка ошибок через **@ControllerAdvice**.  
- Возврат понятных сообщений об ошибках:
  - Некорректный формат данных.  
  - Нехватка носков на складе.  
  - Ошибки при обработке файлов.  

### Документация
- API документировано с помощью **Swagger/OpenAPI**.  

## Установка и запуск проекта
1. Клонируйте репозиторий:  
   ```bash
   git clone https://github.com/username/socks-management-system.git
   ```  
2. Перейдите в директорию проекта:  
   ```bash
   cd socks-management-system
   ```  
3. Соберите проект с помощью Gradle:  
   ```bash
   ./gradlew build
   ```  
4. Запустите приложение:  
   ```bash
   java -jar build/libs/socks-management-system.jar
   ```  

## Конфигурация
**Пример файла application.properties:**  
```
spring.datasource.url=jdbc:postgresql://localhost:5432/Socks_management_system
spring.datasource.username=postgres
spring.datasource.password=root
```  

## Контакты
Автор: Артём Ключаров  
GitHub: [github.com/fiodop](https://github.com/fiodop)  
Email: artemklucarov666@gmail.com

