# 🎯 Testing External Running Application

## Overview

This test suite has been configured to test an **already running Quarkus application** instead of starting an embedded test instance. The tests connect to your external application via REST API calls.

## Key Changes Made

### 1. Configuration (`application.properties`)
- Removed Quarkus test-specific configuration
- Added external application URL configuration:
  ```properties
  test.base.url=http://localhost:8081
  test.base.path=
  ```

### 2. Base Test Class (`BaseTest.java`)
- Created a new base class that all test classes extend
- Configures REST Assured to connect to external application
- Reads configuration from system properties or defaults

### 3. Test Classes
- Removed `@QuarkusTest` annotation from all test classes
- All test classes now extend `BaseTest`
- Tests use REST Assured to make HTTP calls to the running application

## How to Use

### Step 1: Start Your Application
```bash
# In a separate terminal
cd your-quarkus-project
./mvnw quarkus:dev
```

Wait until you see "Quarkus started" message.

### Step 2: Run the Tests
```bash
# In this project directory
mvn clean test
```

### Step 3: View Results
The test report will show your score automatically.

## Configuration Options

### Option 1: Command Line (Recommended)
```bash
mvn clean test -Dtest.base.url=http://localhost:8080
```

### Option 2: Environment Variable
```bash
export TEST_BASE_URL=http://localhost:8080
mvn clean test
```

### Option 3: Edit application.properties
```properties
test.base.url=http://localhost:8080
test.base.path=/api
```

## Important Notes

1. **Application Must Be Running**: The tests will fail with "Connection refused" if your application is not running
2. **Clean State**: For consistent results, ensure your database is in a clean state before running tests
3. **Port Configuration**: Default is `http://localhost:8081`, change if your app runs on a different port
4. **Independent Tests**: Each test is independent but they all connect to the same running application instance

## Troubleshooting

### Connection Refused Error
```bash
# Check if your application is running
curl http://localhost:8081/q/health/live

# If not, start it
cd your-quarkus-project
./mvnw quarkus:dev
```

### Wrong Port
```bash
# Test with different port
mvn clean test -Dtest.base.url=http://localhost:8080
```

### Database State Issues
```bash
# If tests fail due to existing data, restart your application
# This will reset the H2 in-memory database (if using H2)
```

## Architecture

```
┌─────────────────────────────────────┐
│   Test Suite (This Project)         │
│                                      │
│  ┌────────────────────────────────┐ │
│  │ BaseTest                       │ │
│  │ - Configures REST Assured      │ │
│  │ - Sets base URL                │ │
│  └────────────────────────────────┘ │
│           ▲                          │
│           │ extends                  │
│  ┌────────┴────────────────────────┐│
│  │ All Test Classes                ││
│  │ - CRUDOperationsTests           ││
│  │ - ValidationTests               ││
│  │ - HTTPRequirementsTests         ││
│  │ - etc.                          ││
│  └─────────────────────────────────┘│
└──────────────┬──────────────────────┘
               │ HTTP Requests
               │ (REST Assured)
               ▼
┌──────────────────────────────────────┐
│   Your Running Quarkus Application   │
│   (External Process)                 │
│                                      │
│   http://localhost:8081              │
└──────────────────────────────────────┘
```

## Benefits

✅ Tests real application behavior
✅ No embedded test instance overhead
✅ Can test deployed applications
✅ Flexible URL configuration
✅ Same tests work for dev, staging, production

## Made with Bob