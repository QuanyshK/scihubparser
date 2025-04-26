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
                    println("🔍 Connecting to: $fullUrl")
                    val doc = Jsoup.connect(fullUrl)
                        .timeout(10_000)
                        .get()

                    val embed = doc.selectFirst("embed[type=application/pdf]")
                    val src = embed?.attr("src")
                    val pdfUrl = src?.let {
                        if (it.startsWith("//")) "https:$it" else it
                    }

                    val title = doc.selectFirst("#citation i")?.text()

                    if (!pdfUrl.isNullOrEmpty()) {
                        println("✅ Found PDF URL: $pdfUrl")
                        return@withContext Result(pdfUrl, title)
                    }
                } catch (e: Exception) {
                    println("❌ Error connecting to $fullUrl: ${e.message}")
                    continue
                }
            }
            println("🚫 PDF not found on all domains.")
            null
        }
    }
}
