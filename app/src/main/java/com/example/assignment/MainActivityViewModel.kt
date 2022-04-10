package com.example.assignment

import androidx.lifecycle.*
import com.example.assignment.AppConstants.DEFAULT_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import it.skrape.core.document
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.MutableList
import kotlin.collections.Set
import kotlin.collections.forEach
import kotlin.collections.mutableListOf
import kotlin.collections.set
import kotlin.collections.toList
import kotlin.collections.toTypedArray

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private var _contentResponse = MutableLiveData<DataState<ContentResponse>>()
    val contentResponse: LiveData<DataState<ContentResponse>> = _contentResponse

    private val taskValues: ContentResponse by lazy {
        ContentResponse()
    }

    /**
    call all three functions simultaneously
     */
    fun callAllThreeRequests() {
        val job = viewModelScope.launch {
            withContext(Dispatchers.IO) { callRequest1() }
            withContext(Dispatchers.IO) { callRequest2() }
            withContext(Dispatchers.IO) { callRequest3() }
        }
    }

    /**
    request1 will fetch content and give the 10th character
     */
    private suspend fun callRequest1() =
        withContext(Dispatchers.IO) {
            var nthNumberValues: String = "1. Truecaller10thCharacterRequest:\n" +
                    "I. Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from" +
                    "the web\n" +
                    "II. Find the 10th character and display it on the screen\n\n"

            try {
                val contentResponse = skrape(AsyncFetcher) {
                    request {
                        url = AppConstants.url
                        timeout = AppConstants.requestTimeOut
                        followRedirects = true
                        sslRelaxed = true
                    }
                    response {
                        ContentResponse(
                            httpStatusCode = status { code },
                            httpStatusMessage = status { message },
                            wholeText = document.text
                        )
                    }
                }
                nthNumberValues +=
                    everyNth(contentResponse.wholeText.replace("\\s".toRegex(), ""), 10)!!

                nthNumberValues += "\n\n"

                taskValues.wholeText = contentResponse.wholeText.replace("\\s".toRegex(), " ")
                taskValues.request1Content = nthNumberValues
                _contentResponse.postValue(DataState.success(taskValues))
            } catch (exception: Exception) {
                _contentResponse.postValue(DataState.error(exception.message.toString()))
            }
        }

    /**
    request2 will fetch and give the 10th character array
     */
    private suspend fun callRequest2() {
        var nthNumberArrayValues: String = "2. TruecallerEvery10thCharacterRequest:\n" +
                "I. Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from\n" +
                "the web\n" +
                "II. Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen\n\n"

        try {

            val contentResponse = skrape(AsyncFetcher) {
                request {
                    url = AppConstants.url
                    timeout = AppConstants.requestTimeOut
                    followRedirects = true
                    sslRelaxed = true
                }
                response {
                    ContentResponse(
                        httpStatusCode = status { code },
                        httpStatusMessage = status { message },
                        wholeText = document.text
                    )
                }
            }

            nthNumberArrayValues += everyNth(
                contentResponse.wholeText.replace("\\s".toRegex(), ""), 10
            )!!.map { it.toString() }.toTypedArray().toList().toString()

            nthNumberArrayValues += "\n\n"

            taskValues.wholeText = contentResponse.wholeText.replace("\\s".toRegex(), " ")
            taskValues.request2Content = nthNumberArrayValues
            _contentResponse.postValue(DataState.success(taskValues))
        } catch (exception: Exception) {
            _contentResponse.postValue(DataState.error(exception.message.toString()))
        }
    }

    /**
    request3 will fetch and give the unique and repeated words
     */
    private suspend fun callRequest3() {

        try {

            val contentResponse = skrape(AsyncFetcher) {
                request {
                    url = AppConstants.url
                    timeout = AppConstants.requestTimeOut
                    followRedirects = true
                    sslRelaxed = true
                }
                response {
                    ContentResponse(
                        httpStatusCode = status { code },
                        httpStatusMessage = status { message },
                        wholeText = document.text
                    )
                }
            }

            val values =
                printUniqueAndRepeatedWords(contentResponse.wholeText.split(" ").toString())

            val sb = StringBuilder()
            sb.append(
                "\n3. TruecallerWordCounterRequest:\n" +
                        "I. Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from\n" +
                        "the web\n" +
                        "II. Split the text into words using whitespace characters (i.e. space, tab, line break, etc.),\n" +
                        "count the occurrence of every unique word (case insensitive) and display the count for each\n" +
                        "word on the screen\n\n"
            ).append("Unique Words \n\n").append(values.first).append("\n\n Repeated words \n\n")
                .append(values.second)

            taskValues.wholeText = contentResponse.wholeText.replace("\\s".toRegex(), " ")
            taskValues.request3Content = sb.toString()

            _contentResponse.postValue(DataState.success(taskValues))
        } catch (exception: Exception) {
            _contentResponse.postValue(DataState.error(exception.message.toString()))
        }
    }

    /**
    Find the 10th character and display it on the screen
     */
    private suspend fun everyNth(str: String, nthCharacter: Int): String? {
        var displayNthCharacter: String? = DEFAULT_STRING
        withContext(Dispatchers.Default)
        {
            var i = nthCharacter - 1
            while (i < str.length) {
                displayNthCharacter += str[i] + " "
                i += nthCharacter
            }
        }
        return displayNthCharacter
    }

    /**
    @get unique words and adds in mutable list
    @get repeated words and adds in mutable list
     */

    private suspend fun printUniqueAndRepeatedWords(str: String?): Pair<String, String> {

        val uniqueWords: MutableList<String> = mutableListOf()
        val repeatedWords: MutableList<String> = mutableListOf()

        var uniqueWordsString = ""
        var repeatedWordsString = ""

        withContext(Dispatchers.Default)
        {

            val pattern: Pattern = Pattern.compile("[a-zA-Z]+")
            val matcher: Matcher = pattern.matcher(str!!.lowercase(Locale.US))

            val hashmap: HashMap<String, Int> = HashMap()

            while (matcher.find()) {
                val word: String = matcher.group()

                if (!hashmap.containsKey(word)) {
                    hashmap[word] = 1
                } else {
                    hashmap[word] = hashmap[word]!! + 1
                }
            }

            val sets: Set<String> = hashmap.keys
            val iterator = sets.iterator()
            while (iterator.hasNext()) {
                val w = iterator.next()
                if (hashmap[w] == 1) {
                    uniqueWords.add(w)
                } else {
                    repeatedWords.add(w + " = repeated " + hashmap[w] + " times")
                }
            }
        }

        uniqueWords.forEach {
            uniqueWordsString += "-> $it = characters count ${it.length}\n"
        }

        repeatedWords.forEach {
            repeatedWordsString += "-> $it\n"
        }

        return Pair(uniqueWordsString, repeatedWordsString)
    }
}
