# Assignment

# ***First of All TrueCaller is banned in my country so I went into many problems but end up using VPN for Fetching Blog Content***

# I researched on how to do web scraping and amazingly there are many ways for this some people are using Http Networking libraries
# but I used third party library as a shortcut and Initially I have selected two libraries Jsoup and ScrapeIt for getting html content
# from webpages and end up using scrapeIt due to benefits of using coroutines which is recommended framework for doing threading stuff.

# I keep the Architecture of the Application as simple as possible because I totally focused on functionality not making 
# it to much complex and completed the assignment in only two days as I don't have that much time because I have to do some office work 
# and have a deadline of tasks to be completed next week other than that If I have completely utilized my assignment deadline of four days 
# then I may do this assignment in a different way or use Http network library for fetching data and make a clean architecture approach 
# using Solid principles,OOP,MVVM architecture pattern and unit test cases.

#Below is my sample app repository for clean architecture using MVVM and here i write unit test cases too       
#https://github.com/ShehrozMirza/MovieDB_Assignment.git

# Requirements
# "Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from web".
# Request-I.  Find the 10th character and display it on the screen.
# Request-II. Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen.
# Request-III. Split the text into words using whitespace characters (i.e. space, tab, line break, etc.) count the occurrence of every
# unique word (case insensitive) and display the count for each word on the screen.

# App Approach
# Make the UI of app very simple just one Textview and button inside scrollview.
# Use Coroutines for calling 3 requests simultaneously.
# Use Sealed class for DataState.
# Use ViewModel for placeholder data.
# Use Hilt for injecting components and use "by" delegation for injecting viewModel.
# Add GitIgnore file.

# Assumptions 
# Can I use third party libraries?
# I assumed it is okay to use ScrapeIt library.

# Do I have to build a complex UI?
# I assumed whole assignment task can be done in just one activity and I keep the UI very simple. 