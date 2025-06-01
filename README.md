# 🔢 BinaryBox App

> Convert any file into a secure binary string (0 & 1) and back—entirely in your browser.

[![Built with Flutter](https://img.shields.io/badge/Made%20with-Flutter-blue.svg)](https://flutter.dev/)  
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE.md)  
[![Version](https://img.shields.io/badge/Version-1.0.0-green.svg)]()

---

## 📋 Table of Contents

1. [About](#about)  
2. [Key Features](#key-features)  
3. [Live Demo & Screenshots](#live-demo--screenshots)  
4. [Getting Started](#getting-started)  
   - [Prerequisites](#prerequisites)  
   - [Installation](#installation)  
5. [Usage](#usage)  
6. [Tech Stack & Architecture](#tech-stack--architecture)  
7. [Roadmap](#roadmap)  
8. [Contributing](#contributing)  
9. [License](#license)  
10. [Contact](#contact)  

---

## 📖 About

**BinaryBox** is a lightweight, browser-powered Flutter application that allows users to:

- 🔒 **Securely transform** any file (PDF, DOCX, PNG, MP4, etc.) into a binary string of 0s and 1s—instantly and **100% locally**.  
- 🔁 **Restore** that binary string back to its original file format whenever needed.  

No file uploads to external servers. Your data remains private and offline at all times—perfect for sensitive documents and secure offline storage.

---

## ✨ Key Features

1. **File → Binary**  
   - Pick any file from your device.  
   - Instantly generate its full binary representation (0 & 1) in a text field.  
   - Copy or download the binary string for secure sharing or archival.

2. **Binary → File**  
   - Paste or load a binary text file into the app.  
   - Convert the binary back into the original file format.  
   - Download the restored file with the same name and extension.

3. **📐 Background Color Picker**  
   - Personalize your workspace by switching between light, dark, or custom background colors.  
   - Choose from a palette of preset themes or enter your own hex/RGB color code.

4. **🔒 100% Local Conversion**  
   - All conversions run entirely in your browser (no internet needed).  
   - Ensures **zero data leakage**—your files never leave your device.

5. **🖥️ Responsive & Accessible UI**  
   - Clean, intuitive layout with large touch targets and readable fonts.  
   - Works on desktop, tablet, and mobile browsers (Chrome, Firefox, Safari, Edge).

---

## 🌐 Live Demo & Screenshots

> **Demo Link:** [https://your-domain.com/binarybox](https://your-domain.com/binarybox)  
> (Ensure you replace this placeholder with your actual hosted URL or GitHub Pages link.)

| Welcome & Mode Selection | File → Binary Conversion | Binary → File Conversion | Background Color Picker |
|:-----------------------:|:------------------------:|:-------------------------:|:-----------------------:|
| ![](./screenshots/home_screen.png) | ![](./screenshots/file_to_binary.png) | ![](./screenshots/binary_to_file.png) | ![](./screenshots/color_picker.png) |

> _Screenshots above are for reference. Replace with your actual `screenshots/*.png` assets._

---

## 🚀 Getting Started

Follow these steps to run **BinaryBox** locally or host it as a static web app.

### Prerequisites

- **Flutter SDK** 2.0 or higher  
- A modern **web browser** (Chrome, Firefox, Edge, or Safari)  
- **Git** (for cloning the repo)  

> _Note: Although built with Flutter, the final app runs in the browser—no mobile device required._

### Installation

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/<your-username>/BinaryBox.git
   cd BinaryBox