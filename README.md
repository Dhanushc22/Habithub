# 🧠 BinaryBox

Convert any file into 0s and 1s — and back again — all inside your browser.  
No upload. No tracking. 100% private.

---

## 🚀 What is BinaryBox?

BinaryBox is a simple web tool built using Flutter. It lets you:

- 📁 Turn any file (like PDF, PNG, MP4) into a long string of **binary (0s and 1s)**  
- 🔁 Convert that binary string **back into the original file**  
- 🎨 Change the background color for fun and comfort  
- 🔐 All of it happens **in your browser** — no internet or server needed

Great for saving files in plain text format, hiding data, or just learning how binary works!

---

## 🖼️ Screenshots

> _Make sure to add your real images in the `assets/screenshots/` folder or use image links if hosted online._

| Home Screen | File → Binary | Binary → File | Color Picker |
|-------------|----------------|----------------|---------------|
| ![Home](assets/screenshots/home.png) | ![To Binary](assets/screenshots/file_to_binary.png) | ![To File](assets/screenshots/binary_to_file.png) | ![Color](assets/screenshots/color_picker.png) |

---

## 🛠 How It Works

### 📁 File to Binary

1. Click **Pick File**  
2. BinaryBox reads the file and turns it into 0s and 1s  
3. You can **copy** or **download** the binary string as `.txt`

### 🔁 Binary to File

1. Paste binary code or upload `.txt` with 0s and 1s  
2. Click **Convert Binary to File**  
3. The original file is restored and downloaded

### 🎨 Background Color

- Click the color palette icon 🎨  
- Pick a preset or enter your own color  
- Changes background instantly

---

## 🧰 Built With

- **Flutter Web** (Dart)  
- Works in Chrome, Firefox, Edge, Safari  
- No backend — all runs in the browser  
- Byte handling using `Uint8List` and `toRadixString(2)`

---

## 🔧 How to Run Locally

### Requirements

- [Flutter](https://flutter.dev/docs/get-started/install) (latest)  
- Chrome browser

### Steps

```bash
git clone https://github.com/your-username/binarybox.git
cd binarybox
flutter pub get
flutter run -d chrome