# SciHubParser

A lightweight Kotlin library to find and retrieve PDF links from [Sci-Hub](https://sci-hub.se) domains by DOI.

---

## Features

- Easy parsing of PDF download links from multiple Sci-Hub mirrors.
- Coroutine support (`suspend fun`).
- Minimal dependencies (`Jsoup` only).
- Clean and simple API.
- Ready for integration into any Android or Kotlin project.

---

## Installation

Add the dependency to your `build.gradle`:
Kotlin DSL (`build.gradle.kts`):

```kotlin
dependencies {
    implementation("com.github.quanyshk:scihubparser:1.1.0")
}
```

---

## Usage

```kotlin
import com.example.scihubparser.SciHubParser
import kotlinx.coroutines.*

fun fetchPdf(doi: String) {
    CoroutineScope(Dispatchers.Main).launch {
        val parser = SciHubParser()
        val result = parser.parse(doi)

        if (result != null) {
            println("PDF Link: ${result.pdfUrl}")
            println("Title: ${result.title}")
        } else {
            println("PDF not found.")
        }
    }
}
```

---

## API

| Method | Description |
|:------|:-------------|
| `parse(doi: String): Result?` | Parses Sci-Hub mirrors and returns PDF URL and article title if found. |

**Result fields:**
- `pdfUrl: String` \u2014 Direct link to the PDF file.
- `title: String?` \u2014 Title of the article (optional).

---

## Requirements

- Kotlin 1.8+
- Coroutine support
- Internet permission (for Android)

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

---

## Disclaimer

> This library is for educational purposes only.  
> Use responsibly and comply with all applicable laws.
