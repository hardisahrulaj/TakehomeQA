# Automation-Postman — Petstore API Test (CASE02)

Proyek API testing untuk **https://petstore.swagger.io/v2** menggunakan Postman Collection dengan:
- Request chaining via `pm.environment.set` / `pm.environment.get`
- Assertion: status code, response body, schema, content-type, response time
- Randomized ID di setiap run untuk menghindari konflik data
- Report otomatis via Newman (HTML & JSON)

---

## Requirements

| Komponen | Versi |
|---|---|
| Node.js | v18.x atau lebih baru |
| Newman | v6.x (`npm install -g newman`) |
| newman-reporter-htmlextra | v1.22.x (`npm install -g newman-reporter-htmlextra`) |
| Postman | v11.x (opsional, untuk import manual) |
| OS | Windows / macOS / Linux |

---

## Struktur Project

```
Automation-Postman/
├── petstore_collection.json    ← Postman Collection (16 test case)
├── petstore_environment.json   ← Environment variables (baseUrl, petId, orderId, username)
├── reports/                    ← Output report Newman (dibuat otomatis)
│   ├── report.html
│   └── report.json
└── README.md
```

---

## A. Test Case Design (16 Skenario)

| Test Case ID | Judul Test Case | Endpoint | Method | Step | Actual Result | Expected Result |
|---|---|---|---|---|---|---|
| TC001 ✅ | Add New Pet dengan data valid | `/pet` | POST | 1. Generate random `petId` di pre-request. 2. Kirim body JSON `name="BuddyTest"`, `status="available"`. 3. Simpan `id` response ke environment `petId` | Status 200, body berisi `id`, `name: "BuddyTest"`, `status: "available"`, `photoUrls` array | Status 200, body berisi `id`, `name: "BuddyTest"`, `status: "available"`, `photoUrls` array |
| TC002 ✅ | GET Pet by ID valid (chained TC001) | `/pet/{petId}` | GET | 1. Gunakan `{{petId}}` dari environment. 2. Kirim GET request | Status 200, `id` sesuai `petId`, `name: "BuddyTest"`, `status: "available"` | Status 200, `id` sesuai `petId`, `name: "BuddyTest"`, `status: "available"` |
| TC003 ✅ | Update Pet dengan data valid (chained TC001) | `/pet` | PUT | 1. Gunakan `{{petId}}` dari environment. 2. Kirim body `name="BuddyUpdated"`, `status="sold"` | Status 200, `name: "BuddyUpdated"`, `status: "sold"` | Status 200, `name: "BuddyUpdated"`, `status: "sold"` |
| TC004 ✅ | GET Pet by Status "available" | `/pet/findByStatus` | GET | 1. Kirim query param `status=available`. 2. Validasi 3 item pertama dalam array | Status 200, response array tidak kosong, tiap item `status: "available"` | Status 200, response array tidak kosong, tiap item `status: "available"` |
| TC005 ✅ | DELETE Pet by ID valid (chained TC001) | `/pet/{petId}` | DELETE | 1. Gunakan `{{petId}}` dari environment. 2. Kirim header `api_key: special-key` | Status 200, `code: 200`, `message` berisi nilai `petId` | Status 200, `code: 200`, `message` berisi nilai `petId` |
| TC006 ❌ | GET Pet by ID tidak ada (Negatif) | `/pet/9999999999` | GET | 1. Kirim GET dengan ID `9999999999` yang tidak ada | Status 404, `type: "error"`, `message: "Pet not found"` | Status 404, `type: "error"`, `message: "Pet not found"` |
| TC007 ❌ | GET Pet by ID non-numerik (Negatif) | `/pet/abc123` | GET | 1. Kirim GET dengan ID berupa string alfanumerik | Status 404, response body memiliki `type` dan `message` | Status 404, response body memiliki `type` dan `message` |
| TC008 ❌ | POST Add Pet dengan body kosong (Negatif) | `/pet` | POST | 1. Kirim POST dengan body kosong dan Content-Type application/json | Status 405, response body memiliki `message` | Status 405, response body memiliki `message` |
| TC009 ❌ | DELETE Pet by ID tidak ada (Negatif) | `/pet/9999999999` | DELETE | 1. Kirim DELETE dengan ID yang tidak ada, header `api_key: special-key` | Status 404 | Status 404 |
| TC010 ✅ | Place Order dengan data valid | `/store/order` | POST | 1. Generate random `orderId` di pre-request. 2. Kirim body `status="placed"`, `complete=true`. 3. Simpan `id` ke environment `orderId` | Status 200, `id` ada, `status: "placed"`, `complete: true` | Status 200, `id` ada, `status: "placed"`, `complete: true` |
| TC011 ✅ | GET Order by ID valid (chained TC010) | `/store/order/{orderId}` | GET | 1. Gunakan `{{orderId}}` dari environment. 2. Kirim GET request | Status 200, `id` sesuai, `status: "placed"`, `complete: true` | Status 200, `id` sesuai, `status: "placed"`, `complete: true` |
| TC012 ✅ | GET Store Inventory | `/store/inventory` | GET | 1. Kirim GET tanpa parameter | Status 200, response berupa object tidak kosong (map status → jumlah) | Status 200, response berupa object tidak kosong (map status → jumlah) |
| TC013 ❌ | GET Order by ID tidak ada (Negatif) | `/store/order/9999999` | GET | 1. Kirim GET dengan orderId yang tidak ada | Status 404, `message: "Order not found"` | Status 404, `message: "Order not found"` |
| TC014 ✅ | Create User dengan data valid | `/user` | POST | 1. Generate random `username` di pre-request. 2. Kirim body user lengkap. 3. Simpan `username` ke environment | Status 200, `code: 200`, `message` berisi userId (string) | Status 200, `code: 200`, `message` berisi userId (string) |
| TC015 ✅ | GET User by Username valid (chained TC014) | `/user/{username}` | GET | 1. Gunakan `{{username}}` dari environment. 2. Kirim GET request | Status 200, `username` sesuai, `email: "testuser@example.com"` | Status 200, `username` sesuai, `email: "testuser@example.com"` |
| TC016 ❌ | GET User by Username tidak ada (Negatif) | `/user/usertidakada_xyz99999` | GET | 1. Kirim GET dengan username yang tidak terdaftar | Status 404, `type: "error"`, `message: "User not found"` | Status 404, `type: "error"`, `message: "User not found"` |

**Ringkasan:** 9 Positif (TC001–TC005, TC010–TC012, TC014–TC015) · 7 Negatif (TC006–TC009, TC013, TC016) · **Total 16 skenario**

---

## B. Chaining Flow & Environment Variables

```
Collection: Petstore API Test - CASE02
│
├── 📁 Pet
│   ├── TC001 POST /pet              ← pre-request: set petId random
│   │                                  test: pm.environment.set('petId', json.id)
│   ├── TC002 GET  /pet/{{petId}}    ← baca petId dari TC001
│   ├── TC003 PUT  /pet              ← baca petId dari TC001 (di body & URL)
│   ├── TC004 GET  /pet/findByStatus ← independen
│   ├── TC005 DEL  /pet/{{petId}}    ← baca petId dari TC001
│   ├── TC006 GET  /pet/9999999999   ← negatif independen
│   ├── TC007 GET  /pet/abc123       ← negatif independen
│   ├── TC008 POST /pet (body kosong)← negatif independen
│   └── TC009 DEL  /pet/9999999999  ← negatif independen
│
├── 📁 Store
│   ├── TC010 POST /store/order      ← pre-request: set orderId random
│   │                                  test: pm.environment.set('orderId', json.id)
│   ├── TC011 GET  /store/order/{{orderId}} ← baca orderId dari TC010
│   ├── TC012 GET  /store/inventory  ← independen
│   └── TC013 GET  /store/order/9999999    ← negatif independen
│
└── 📁 User
    ├── TC014 POST /user             ← pre-request: pm.environment.set('username', 'testuser_'+seed)
    ├── TC015 GET  /user/{{username}}← baca username dari TC014
    └── TC016 GET  /user/usertidakada_xyz99999 ← negatif independen
```

| Environment Variable | Di-set di | Digunakan di |
|---|---|---|
| `baseUrl` | `petstore_environment.json` | Semua request |
| `petId` | TC001 pre-request + test script | TC002, TC003, TC005 |
| `orderId` | TC010 pre-request + test script | TC011 |
| `username` | TC014 pre-request script | TC015 |

---

## C. Cara Menjalankan Test

### 1. Install Dependencies

```bash
npm install -g newman
npm install -g newman-reporter-htmlextra
```

### 2. Verifikasi Instalasi

```bash
newman --version
```

### 3. Jalankan Test (CLI output saja)

```bash
newman run petstore_collection.json -e petstore_environment.json
```

### 4. Generate HTML Report

```bash
newman run petstore_collection.json -e petstore_environment.json -r htmlextra --reporter-htmlextra-export reports/report.html
```

### 5. Generate JSON Report

```bash
newman run petstore_collection.json -e petstore_environment.json -r json --reporter-json-export reports/report.json
```

### 6. Generate Semua Report Sekaligus (Rekomendasi)

```bash
newman run petstore_collection.json -e petstore_environment.json -r cli,htmlextra,json --reporter-htmlextra-export reports/report.html --reporter-json-export reports/report.json
```

Buka `reports/report.html` di browser untuk melihat hasil lengkap.

---

## D. Import ke Postman (Opsional)

1. Buka **Postman** → klik **Import**
2. Pilih file `petstore_collection.json` → klik **Import**
3. Buka **Environments** → klik **Import** → pilih `petstore_environment.json`
4. Aktifkan environment **Petstore Environment - CASE02**
5. Klik **Run Collection** untuk menjalankan semua TC

---

## E. Catatan Teknis

| Topik | Keterangan |
|---|---|
| Randomized ID | `Math.random()` di pre-request script mencegah konflik ID di server publik |
| Chaining | `pm.environment.set()` di test script TC001/TC010/TC014, dibaca via `{{variable}}` di request berikutnya |
| TC007 | GET `/pet/abc123` → API Petstore mengembalikan 404 (bukan 400), karena path dianggap not found |
| TC008 | POST body kosong → API Petstore mengembalikan 405 (Invalid Input / Method Not Allowed) |
| TC009 | DELETE ID tidak ada → API Petstore mengembalikan 404 |
| api_key | Header `api_key: special-key` wajib untuk endpoint DELETE `/pet/{id}` |
| Server publik | `petstore.swagger.io` adalah shared server — data bisa diubah user lain kapan saja |
