
# Blood donation

Application where we can add new entries to database which holds information about people that donated blood, like personal data (name,last name,age,sex, social number etc.), address and information about blood donation itself (blood type, amount, date, time).


## About

Application is used for learning purposes only.
- written in JAVA
- GUI made using only SWING
- database created in MySQL
Aim for this project was to learn about databases in general, SQL language, functions like procedures,triggers,functions (used internally), connectivity (using JDBC). 

At the same time learned new features in SWING components, for example setting custom filter in text field components (like only numbers, only letters, certain amount of characters or applying specific regular expression - like in zip code xx-xxx).

Database is simple having only few tables (simple relationships) and few records with random data to check if all functions work properly.

Look and Feel : flatlaf API made by FormDev (https://www.formdev.com/flatlaf/)
## In action

Application first require log in to database with predetermined account 
- username "Rocky"
- password "12345"
Data is already filled to make it easier to use so we don't have to type it multiple times while testing (All info about account used or JDBC driver is available in .properties file, which allows modify something without looking into code).

[![H4oRJG2.th.png](https://iili.io/H4oRJG2.th.png)](https://freeimage.host/i/H4oRJG2)

After successful log in, we're presented with menu where we can add new entry, update entry or show some statistics (with predefined queries, displayed in form of tables exacly like in RMDB).

[![H4oRYua.th.png](https://iili.io/H4oRYua.th.png)](https://freeimage.host/i/H4oRYua)

While adding entry, every text field is checked if not empty or has proper format. After submiting, firstly mobile phone number is checked. Database contains every valid prefixes for Polish mobile phone numbers (taken from : https://www.operatorzy.pl/telekomunikacja/numeracja/numeracja-sieci-komorkowych)

[![H4oAyQ4.th.png](https://iili.io/H4oAyQ4.th.png)](https://freeimage.host/i/H4oAyQ4)

To update patient data, input name and last name to fetch data form database. New panel will appear with filled data which we can modify and submit. (tip : we can check statistics to quickly check what entries are in database).

[![H4oRHCl.th.png](https://iili.io/H4oRHCl.th.png)](https://freeimage.host/i/H4oRHCl)

[![H4oApjf.th.png](https://iili.io/H4oApjf.th.png)](https://freeimage.host/i/H4oApjf)

Statistics window show some predefined query results like show all patients, all addresses, all donations. New queries can be easly add in code.

[![H4oRd4S.th.png](https://iili.io/H4oRd4S.th.png)](https://freeimage.host/i/H4oRd4S)



## EER Driagram
[![H4olCwF.th.png](https://iili.io/H4olCwF.th.png)](https://freeimage.host/i/H4olCwF)

 
 
