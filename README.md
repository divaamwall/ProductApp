📱 Product Shopping App
Aplikasi E-Commerce sederhana berbasis Android yang dibuat menggunakan Jetpack Compose dan Clean Architecture. Aplikasi ini memungkinkan pengguna untuk menjelajahi daftar produk dengan fitur paginasi, melakukan pengurutan (sorting), dan mengelola keranjang belanja hingga proses checkout.

🚀 Fitur Utama
Paging Data: Memuat produk secara bertahap menggunakan Paging Library untuk efisiensi memori.

Dynamic Sorting: Mengurutkan produk berdasarkan Nama (A-Z), Harga Terendah, atau Harga Tertinggi.

Product Cart System:

Menambah/mengurangi jumlah produk langsung dari daftar.

Validasi stok produk secara real-time.

Kalkulasi otomatis total harga di bagian footer.

Checkout Success Dialog: Ringkasan pembelian setelah transaksi berhasil.

🛠️ Tech Stack
UI: Jetpack Compose (Material 3)

Asynchronous: Kotlin Coroutines & Flow

Networking: Retrofit & OkHttp

Image Loading: Coil

Architecture: Clean Architecture (Data, Domain, Presentation)

📖 Petunjuk Penggunaan
1. Menjelajahi Produk
Saat aplikasi dibuka, daftar produk akan dimuat secara otomatis. Anda dapat menggulir ke bawah, dan aplikasi akan memuat data baru (paginasi) tanpa mengganggu pengalaman pengguna.

2. Menggunakan Fitur Sorting
Klik tombol "Sort by" di bagian kanan atas (di bawah Header).

Pilih opsi pengurutan yang diinginkan.

Daftar produk akan diperbarui secara otomatis sesuai kriteria yang dipilih.

3. Mengelola Keranjang Belanja
Tekan tombol (+) pada kartu produk untuk menambah item ke keranjang.

Tekan tombol (-) untuk mengurangi jumlah.

Aplikasi akan mencegah penambahan jika jumlah melebihi stok yang tersedia.

Lihat total biaya belanja Anda secara real-time di bar bagian bawah (Footer).

4. Checkout
Setelah selesai memilih produk, klik tombol "Checkout".

Akan muncul pop-up sukses yang menampilkan total produk dan total biaya yang harus dibayar.

Klik "Close" untuk mengosongkan keranjang dan memulai belanja baru.
