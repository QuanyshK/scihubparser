package com.example.scihubparser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class SciHubParser {

    private val sciHubDomains = listOf(
        "https://sci-hub.ru",
        "https://sci-hub.se",
        "https://sci-hub.st",
        "https://sci-hub.wf"
    )

    data class Result(
        val pdfUrl: String,
        val title: String?
    )

    suspend fun parse(doi: String): Result? {
        return withContext(Dispatchers.IO) {
            for (domain in sciHubDomains) {
                val fullUrl = "$domain/$doi"
                try {
                    val doc = Jsoup.connect(fullUrl)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36")
                        .timeout(10_000)
                        .get()

                    if (doc.title().contains("DDoS-Guard", ignoreCase = true)) {
                        kotlinx.coroutines.delay(3000)
                        continue
                    }

                    val embed = doc.selectFirst("embed[type=application/pdf]")
                    val src = embed?.attr("src")
                    val fixedPdfUrl = src?.let { it }

                    val title = doc.selectFirst("#citation i")?.text()

                    if (!fixedPdfUrl.isNullOrEmpty()) {
                        return@withContext Result(fixedPdfUrl, title)
                    }
                } catch (e: Exception) {
                    continue
                }
            }
            null
        }
    }
}
