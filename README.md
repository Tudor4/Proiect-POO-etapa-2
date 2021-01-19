# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Niculescu Tudor-Alexandru, 321CA

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations
* Json
* Json-lib
* Org.json.simple 

## Implementare

### Entitati

Pentru stocarea initiala a datelor au fost folosite clasele din pachetul input, iar pentru rularea jocului au fost stocate in clase din pachetul Data, clase principale ale programului:
Consumer, Distributor, Producer, MonthlyUpdate, Contract, Month si Data.
Pentru a utiliza strategiile distribuitorilor sunt disponibile clasele din pachetul Strategies.

### Flow

Initial se foloseste clasa InputLoader pentru a transfera taote informatiile din fisierul de intrare in clasele din pachetul input. Acestea sunt trimise metodei factory din clasa DataFactory,
de unde datele ajung in clasele principale ale programului. Din acest punct, informatiile sunt prelucrate dupa logica stabilita in cerinta. Toate metodele ce contribuie la simularea unui tur al 
jocului sunt apelate din metoda simulateTurn. La finalul jocului se apeleaza jsonWrite din clasa Writer pentru a scrie rezultatul in formatul dorit. 

### Elemente de design OOP

Am folosit cat mai multe clase si pachete posibile(input, output, data, strategies). Fiecare clasa(in afara de Data) are o singura functionalitate. Pentru a fi usor de urmarit, logica jocului
a fost implementat in Data. Metode precum factory, getData, jsonWrite sau strategy se gasesc in clasele lor aferente, insa metode precum chooseProducers sau payExpenses se regasesc impreuna cu 
toata logica jocului in clasa Data.

### Design patterns

Am folosit Singleton si factory in acelasi loc, rezultatul fiind un singleton factory(clasa DataFactory). Pentru cele 3 strategii folosite de distribuitorii am folosit Strategy pattern.
Interfata Strategy impreuna cu cele 3 clase GreenStrategy, PriceStrategy si QuantityStrategy se regasesc in pachetul strategies. Pattern-ul observer l-am implementat in felul urmator: in 
momentul in care un producator isi schimba cantitatea de energie per distribuitor, fiecare distribuitor care lua energie de la acest producator este notificat, variabila booleana notified
este setata pe true. In momentul in apelarii metodei chooseProducers, se verifica daca notified este true sau false. Daca este true, se aplica strategia aleasa de distribuitor iar apoi variabila
va primi valoarea false.

### Repository

<https://github.com/Tudor4/Proiect-POO-etapa-2.git>
