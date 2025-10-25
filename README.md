# SciHubParser

[![](https://jitpack.io/v/QuanyshK/scihubparser.svg)](https://jitpack.io/#QuanyshK/scihubparser)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.8+-purple.svg)](https://kotlinlang.org)

A lightweight Kotlin library to find and retrieve PDF links from [Sci-Hub](https://sci-hub.se) domains by DOI.

---

## ✨ Features

- 🔍 Easy parsing of PDF download links from multiple Sci-Hub mirrors
- ⚡ Coroutine support with `suspend fun` for non-blocking operations
- 📦 Minimal dependencies (`Jsoup` only)
- 🎯 Clean and simple API
- 📱 Ready for integration into any Android or Kotlin project
- 🛡️ Error handling with nullable return types
- 🌐 Automatic fallback to alternative Sci-Hub mirrors

---

## 📦 Installation

### Step 1: Add JitPack repository

Add the JitPack repository to your root `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add the dependency

Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.QuanyshK:scihubparser:1.1.0")
}
```

---

## 🚀 Quick Start

### Basic Usage

```kotlin
import com.example.scihubparser.SciHubParser
import kotlinx.coroutines.*

fun main() {
    CoroutineScope(Dispatchers.Main).launch {
        val parser = SciHubParser()
        val doi = "10.1016/j.cell.2020.01.001"
        
        val result = parser.parse(doi)
        
        if (result != null) {
            println("✅ PDF Found!")
            println("📄 Title: ${result.title}")
            println("🔗 PDF Link: ${result.pdfUrl}")
        } else {
            println("❌ PDF not found for DOI: $doi")
        }
    }
}
```

### Android Integration Example

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var parser: SciHubParser
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parser = SciHubParser()
        
        lifecycleScope.launch {
            fetchPdfFromDoi("10.1038/nature12373")
        }
    }
    
    private suspend fun fetchPdfFromDoi(doi: String) {
        try {
            val result = parser.parse(doi)
            
            result?.let {
                // Download or display PDF
                downloadPdf(it.pdfUrl, it.title ?: "document.pdf")
            } ?: run {
                Toast.makeText(this@MainActivity, "PDF not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("SciHubParser", "Error: ${e.message}")
        }
    }
    
    private fun downloadPdf(url: String, filename: String) {
        // Your download logic here
    }
}
```

### Error Handling

```kotlin
suspend fun safeFetchPdf(doi: String): String? {
    return try {
        val parser = SciHubParser()
        val result = parser.parse(doi)
        result?.pdfUrl
    } catch (e: IOException) {
        Log.e("SciHubParser", "Network error: ${e.message}")
        null
    } catch (e: Exception) {
        Log.e("SciHubParser", "Unexpected error: ${e.message}")
        null
    }
}
```

---

## 📚 API Reference

### `SciHubParser` Class

Main class for parsing PDF links from Sci-Hub mirrors.

#### Methods

| Method | Description | Return Type |
|:-------|:------------|:------------|
| `suspend fun parse(doi: String): Result?` | Parses Sci-Hub mirrors and returns PDF URL and article title if found. Returns `null` if not found or on error. | `Result?` |

#### `Result` Data Class

Represents successful parsing result.

| Field | Type | Description |
|:------|:-----|:------------|
| `pdfUrl` | `String` | Direct link to the PDF file |
| `title` | `String?` | Title of the article (may be null if not available) |

---

## ⚙️ Requirements

- **Kotlin**: 1.8 or higher
- **Coroutines**: kotlinx.coroutines library
- **Android**: API level 21+ (Android 5.0 Lollipop)
- **Internet Permission** (for Android apps):

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

### Dependencies

- `Jsoup` - HTML parsing library (automatically included)
- `kotlinx-coroutines` - For async operations

---

## 🔧 Configuration

### Custom Timeout (Future Feature)

Currently, the library uses default timeout settings. Custom configuration will be added in future releases.

### Supported DOI Formats

- Standard DOI: `10.1016/j.cell.2020.01.001`
- DOI with prefix: `doi:10.1038/nature12373`
- DOI URL: `https://doi.org/10.1145/example`

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this library:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add some amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Before Contributing

Please make sure to:
- Write clear, descriptive commit messages
- Add unit tests for new features
- Update documentation accordingly
- Follow Kotlin coding conventions
- Test on Android API 21+ devices

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## ⚠️ Disclaimer

> **Important Notice:**  
> This library is provided **for educational and research purposes only**.  
> 
> - **Legal Compliance**: Using Sci-Hub may violate copyright laws in your jurisdiction. Users are solely responsible for ensuring their usage complies with applicable laws.
> - **Ethical Consideration**: The developers of this library do not encourage or endorse piracy or copyright infringement.
> - **Support Legal Access**: Consider supporting authors and publishers by obtaining papers through legal channels when possible:
>   - University library access
>   - Author direct request
>   - Open Access journals
>   - ResearchGate or Academia.edu
> - **Newer Articles**: For articles published after 2022, consider using [Sci-Net](https://sci-hub.se) for collaborative academic access or check institutional access options.

---

## 🐛 Known Issues & Limitations

- **Mirror Availability**: Some Sci-Hub mirrors may be temporarily unavailable due to blocking or maintenance
- **HTML Changes**: Parsing may fail if Sci-Hub changes its HTML structure
- **Database Coverage**: Sci-Hub database primarily covers articles published before 2022
- **Rate Limiting**: Heavy usage may trigger rate limiting from mirrors
- **Network Dependency**: Library requires stable internet connection

### Reporting Issues

If you encounter any issues or bugs, please [open an issue](https://github.com/QuanyshK/scihubparser/issues) on GitHub with:
- Error message or stack trace
- DOI you were trying to parse
- Your environment (Android API level, Kotlin version, etc.)
- Steps to reproduce

---

## 📧 Contact & Support

- **Author**: QuanyshK
- **GitHub**: [@QuanyshK](https://github.com/QuanyshK)
- **Issues**: [Report a bug](https://github.com/QuanyshK/scihubparser/issues)
- **Email**: Open an issue for inquiries

---

## 🙏 Acknowledgments

- Built with [Jsoup](https://jsoup.org/) for HTML parsing and DOM traversal
- Inspired by the need for accessible academic research
- Sci-Hub project for pioneering open access initiatives
- Kotlin and Android communities for excellent documentation and tools

---

## 📚 Related Resources

- [Sci-Hub Official](https://sci-hub.se) - Access scientific research
- [Jsoup Documentation](https://jsoup.org/) - HTML parser used by this library
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Async programming guide
- [DOI System](https://www.doi.org/) - Learn about DOI identification system

---

## Version History

### v1.1.0
- Initial stable release
- Basic Sci-Hub parsing functionality
- Coroutine support
- Android integration examples

### Planned Features
- Custom timeout configuration
- Retry mechanism with exponential backoff
- Caching support
- Proxy support
- Batch DOI processing

---

## 🚀 Getting Started

### For Android Developers

1. Add JitPack repository to your project
2. Add dependency to your module
3. Request internet permission in AndroidManifest.xml
4. Use `lifecycleScope.launch` to call suspend functions
5. Handle null results and exceptions

### For Kotlin Desktop Developers

1. Add JitPack repository
2. Add dependency
3. Use `runBlocking` or `GlobalScope.launch` for coroutines (use with caution)
4. Implement proper error handling

---

**Last Updated**: October 2025  
**License**: MIT  
**Status**: Active Development
