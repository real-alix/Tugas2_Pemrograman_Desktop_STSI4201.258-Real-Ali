import java.util.Scanner;

class Menu {

    String nama;
    int harga;
    String kategori;

    Menu(String nama, int harga, String kategori){
        this.nama= nama; 
        this.harga=harga;
        this.kategori=kategori;
    }
}

public class Main {
    static Menu[] daftarMenu = new Menu[100];
    static int jumlahMenu = 0;

    //Method 1 : Input data menu
    static void initMenu(){
        daftarMenu[0] = new Menu("Nasi Goreng", 25000, "makanan");
        daftarMenu[1] = new Menu("Ayam geprek", 20000, "makanan");
        daftarMenu[2] = new Menu("Mie goreng", 20000, "makanan");
        daftarMenu[3] = new Menu("Sate Ayam",30000, "makanan");
        daftarMenu[4] = new Menu("Es Teh", 5000, "minuman");
        daftarMenu[5] = new Menu("Es Jeruk",7000, "minuman");
        daftarMenu[6] = new Menu("Air Mineral", 6000, "minuman");
        daftarMenu[7] = new Menu("Kopi Susu", 15000, "minuman");
        jumlahMenu = 8;        

    }

    //Method 2 : Tampilkan menu per kategori
    static void tampilkanDaftarMenu(){
        System.out.println("===========DAFTAR MENU=============");
        System.out.println("----MAKANAN------");
        for (int i = 0; i < jumlahMenu; i++)    {
            if (daftarMenu[i].kategori.equalsIgnoreCase("makanan")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " - Rp" + daftarMenu[i].harga);                
            }
        }
        System.out.println("-----MINUMAN------");
        for (int i = 0; i < jumlahMenu; i++)    {
            if (daftarMenu[i].kategori.equalsIgnoreCase("minuman")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " - Rp" + daftarMenu[i].harga);                
            }
        }
        System.out.println("===================================");
    }

    //Method 3 : Cari menu (kita pakaI switch func)

    static Menu cariMenu(String nama){
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama.trim())) {
                return daftarMenu[i];
            }
        }
        return null;
    }

    //Method 4 ===============================Proses pemesanan ====================================
    static void prosesPemesanan(Scanner sc) {
        Menu[] pesananMenu = new Menu[100];
        int[]   pesananQty = new  int[100];
        int jumlahPesanan = 0;

        tampilkanDaftarMenu();
        System.out.println("\nMasukan pesanan dengan format : Nama Menu = Jumlah");
        System.out.println("Contoh : Nasi Goreng = 2");
        System.out.println("Ketik 'selesai' jika sudah selesai memesan. \n");

        while (true) {
            System.out.print("Pesanan : ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            if (!input.contains("=")) {
                System.out.println("Format salah ! Contoh : Es Teh = 3");
                continue;
            }

            String[] parts = input.split("=");

            if (parts.length < 2) {
                System.out.println("Format salah! Contoh : Es Teh = 3");
                continue;
            }

            String nama = parts[0].trim();
            int qty;
            try {
                qty = Integer.parseInt(parts[1].trim());
                if (qty <= 0) {
                    System.out.println("Jumlah harus lebih dari 0 !");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Jumlah harus berupa angka!");
                continue;
            }

            Menu ditemukan = cariMenu(nama);
            if (ditemukan == null) {
                System.out.println("Menu \"" + nama + "\" tidak ditemukan ! Silahkan coba lagi. ");
                continue;
            }

            pesananMenu[jumlahPesanan] = ditemukan;
            pesananQty[jumlahPesanan] = qty;
            jumlahPesanan++;
            System.out.println("V " + ditemukan.nama + " x" + qty + " berhasil ditambahkan. ");
        }

        if (jumlahPesanan == 0) {
            System.out.println("Tidak ada pesanan dimasukan. ");
            return ;
        }

        cetakStruk(pesananMenu, pesananQty, jumlahPesanan);
        
        System.out.println("\nTekan ENTER untuk kembali ke menu utama...");
        sc.nextLine();
    }

    // ========================== CETAK STRUK + HITUNG TOTAL ===========================
    static void cetakStruk(Menu[] pesanan, int[] qty, int n) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += pesanan[i].harga * qty[i];
        }

        int pajak = total * 10 / 100;
        int service = 20000;
        int diskon = 0;
        if (total > 100000) {
            diskon = total * 10 /100;
        }

        // Cari minuman untuk bonus (bisa ambil yang pertama ditemukan)
        Menu gratis = null;
        for (int i = 0; i < n; i++) {
            if (pesanan[i].kategori.equalsIgnoreCase("minuman")) {
                gratis = pesanan[i];
                break;
            }
        }

        int bonus = 0;
        if (total > 50000 && gratis != null) {
            bonus = gratis.harga;
        }

        int bayar = total + pajak + service - diskon - bonus;

        System.out.println("\n===============================STRUK PESANAN==============================");
        for (int i = 0; i < n; i++) {
            System.out.println(pesanan[i].nama + qty[i] + " = Rp" + (pesanan[i].harga * qty[i]));
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Total          : Rp " + total);
        System.out.println("Pajak (10%)    : Rp " + pajak);
        System.out.println("Biaya Service Charge   : Rp " + service);

        if (diskon > 0) {
            System.out.println("Diskon 10%      : Rp " + diskon);
        }

        if (bonus > 0) {
            System.out.println("Bonus Gratis    : Rp " + bonus + " (" + gratis.nama + ")");
        }

        System.out.println("---------------------------------------------------");
        System.out.println("TOTAL BAYAR      : Rp " + bayar);
        System.out.println("===================================================");
        
    }

    //===================== MENU PEMILIK ====================================
    static void menuPemilik(Scanner sc) {
        while (true) {
            System.out.println("n----------------KELOLA MENU RESTORAN-----------------------");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.println("Pilih menu: ");
            String pilih = sc.nextLine().trim();

            switch (pilih) {
                case "1":
                    tambahMenu(sc);
                    break;
                case "2":
                    ubahHarga(sc);
                    break;
                case "3":
                    hapusMenu(sc);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan pilih 1-4");;
            }
        }
    }
    
    //========================= TAMBAH MENU =============================
    static void tambahMenu(Scanner sc) {
        if (jumlahMenu >= daftarMenu.length) {
            System.out.println("Kapasitas menu penuh!");
            return;
        }

        System.out.println("\n--- Tambah Menu Baru---");
        System.out.println("Nama menu : ");
        String nama = sc.nextLine().trim();

        if (cariMenu(nama) != null) {
            System.out.println("Menu dengan nama tersebut sudah ada !");
            return; 
        }

        System.out.println("Harga: ");
        int harga;
        try {
            harga = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Harga harus berupa angka! ");
            return;
        }

        System.out.println("Kategori (makanan/minuman): ");
        String kategori = sc.nextLine().trim().toLowerCase();
        if (!kategori.equals("makanan") && !kategori.equals("minuman")) {
            System.out.println("Kategori hanya boleh 'makanan' atau 'minuman' !");
            return;
        }

        daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);
        jumlahMenu++;
        System.out.println("V Menu \"" + nama + "\" berhasil ditambahkan !");
    }

    // ============================== UBAH HARGA ===============================
    static void  ubahHarga(Scanner sc) {
        tampilkanDaftarMenu();
        System.out.println("\nMasukan nomor menu yang ingin diubah harganya: ");
        int nomor;
        try {
            nomor = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Nomor harus berupa angka!");
            return ;
        }

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor menu tidak valid ! ");
            return;
        }

        Menu m = daftarMenu[nomor - 1];
        System.out.println("Menu terpilih: " + m.nama + " (Harga lama: Rp" + m.harga + ")");
        System.out.println("Masukan harga baru: ");
        int hargaBaru;
        try {
            hargaBaru = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Harga harus berupa angka!");
            return;
        }

        System.out.println("Yakin ingin merubah harga? (Ya / Tidak): ");
        String konfirmasi = sc.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            m.harga = hargaBaru;
            System.out.println("V Harga \"" + m.nama + "\" berhasil diubah menjadi Rp" + hargaBaru);
        } else {
            System.out.println("Perubahan dibatalkan.");
        }
    }

    //===================== HAPUS MENU ========================
    static void hapusMenu(Scanner sc) {
        System.out.println("\nMasukan nomor menu yang ingin dihapus: ");
        int nomor;
        try {
            nomor = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Nomor harus berupa angka! ");
            return;
        }

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor menu tidak valid!");
            return;
        }

        Menu m = daftarMenu[nomor - 1];
        System.out.println("Menu terpilih: " + m.nama + " - Rp" + m.harga);
        System.out.println("Yakin ingin menghapus nomor ini? (Ya/Tidak): ");
        String konfirmasi = sc.nextLine().trim();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            for (int i = nomor -1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            daftarMenu[jumlahMenu - 1] = null;
            jumlahMenu--;
            System.out.println("V Menu \"" + m.nama + "\" berhasil dihapus.");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    //========================== MAIN ==========================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initMenu();

        while (true) {
            System.out.println("\n================ APLIKASI RESTORAN========================");
            System.out.println("1. Pesan Makanan / Minuman (Pelanggan)");
            System.out.println("2. Pesan Makanan / Minuman (Pemilik)");
            System.out.println("3. Keluar");
            System.out.println("Pilih menu: ");
            String pilihan = sc.nextLine().trim();

            switch (pilihan) {
                case "1":
                    prosesPemesanan(sc);
                    break;
                case "2":
                    menuPemilik(sc);
                    break;
                case "3":
                    System.out.println("Terima kasih telah menggunakan aplikasi");
                    sc.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid! Silahkan pilih 1,2 atau 3.");
            }
        }
    }
}
