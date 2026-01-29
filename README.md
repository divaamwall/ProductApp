# 📱 Product App

> **Aplikasi E-Commerce modern berbasis Android yang dibangun dengan efisiensi dan skalabilitas menggunakan Clean Architecture.**

Aplikasi ini dirancang untuk memberikan pengalaman belanja yang mulus dengan performa tinggi, memanfaatkan **Jetpack Compose** untuk antarmuka deklaratif dan **Hilt** untuk manajemen dependensi.

---

### 🚀 Fitur Utama

* **📦 Paging Data** – Memuat ribuan produk secara efisien menggunakan *Paging Library* untuk meminimalkan penggunaan memori dan data.
* **⚖️ Dynamic Sorting** – Fitur pengurutan fleksibel berdasarkan Nama (A-Z), Harga Terendah, atau Harga Tertinggi.
* **🛒 Product Cart System** – Manajemen keranjang belanja yang intuitif:
    * Tambah/kurang jumlah produk langsung dari kartu produk.
    * **Validasi Stok**: Sistem secara otomatis mengunci penambahan jika melebihi stok tersedia.
    * Kalkulasi total harga secara *real-time* di bagian footer.
* **✨ Checkout Dialog** – Ringkasan pembelian yang bersih setelah transaksi berhasil dilakukan.

---

### 🛠️ Tech Stack

| Layer | Technology |
| :--- | :--- |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Language** | Kotlin |
| **Reactive Stream** | Kotlin Coroutines & Flow |
| **Networking** | Retrofit & OkHttp |
| **Image Loader** | Coil |
| **Architecture** | Clean Architecture (Data, Domain, Presentation) |

---

### 🏗️ Arsitektur Proyek
Aplikasi ini mengikuti prinsip **Clean Architecture** untuk memastikan kode mudah diuji (*testable*) dan mudah dipelihara:



1.  **Data Layer**: Bertanggung jawab atas pengambilan data dari API (Remote) dan implementasi Repository.
2.  **Domain Layer**: Pusat logika bisnis yang berisi Use Cases dan abstraksi Repository.
3.  **Presentation Layer**: Mengelola UI State menggunakan ViewModel dan merender UI menggunakan Komponen Compose.

---

### 📖 Petunjuk Penggunaan

#### 1. Menjelajahi Produk
Buka aplikasi dan daftar produk akan muncul secara otomatis. Gulir ke bawah untuk memuat data baru secara halus tanpa *lag* berkat integrasi Paging.

#### 2. Menggunakan Fitur Sorting
* Klik tombol **"Sort by"** di bagian kanan atas.
* Pilih kriteria pengurutan yang diinginkan dari menu *dropdown*.
* Daftar produk akan diperbarui secara otomatis sesuai pilihan Anda.

#### 3. Mengelola Keranjang Belanja
* Tekan tombol **(+)** untuk menambah item.
* Tekan tombol **(-)** untuk mengurangi jumlah (item akan dihapus dari keranjang jika jumlah mencapai nol).
* Pantau total biaya belanja Anda di bar bagian bawah (**Footer**).

#### 4. Checkout
* Setelah selesai memilih, klik tombol **"Checkout"**.
* Sebuah pop-up sukses akan muncul menampilkan total produk dan total nominal dalam format Rupiah.
* Klik **"Close"** untuk mereset belanjaan dan memulai sesi baru.

---

### 💻 Cara Instalasi
1. Clone repositori ini:
   ```bash
   git clone [https://github.com/username/product-app.git](https://github.com/username/product-app.git)
