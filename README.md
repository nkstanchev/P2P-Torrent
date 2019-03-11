# Peer-to-peer file exchange (torrent сървър) :busts_in_silhouette:

Да се имплементира опростен вариант на peer-to-peer система за обмен на файлове, която използва централен сървър за откриване на потребители и данни.

## Условие

Системата трябва да се състои от две части:
1.	Сървър, който съхранява метаданни за наличните файлове.
2.	Клиенти, които теглят файлове от други клиенти – клиентите взимат информация от сървъра за това къде (от кой клиент) даден файл може да бъде свален.

Системата предоставя функционалност за сваляне на файлове от различни потребители.

Всеки потребител може да сваля файл от всеки друг. Има централен сървър, който съхранява информация за потребителите и файловете, които могат да бъдат свалени от тях (файлове не се свалят и съхраняват на централния сървър).

Сървърът трябва да може да работи с много клиенти едновременно.

Сървърът съхранява информация за активните потребители в паметта (изберете подходяща структура от данни) – имената, адресите и портовете им (p2p обмен изисква да се знаят адресите на peer-ите); файловете, които могат да бъдат изтеглени от тях (абсолютен път). Тази информация се обновява, когато клиент регистрира файлове за изтегляне (команда *register* по-долу) и когато клиент затвори връзката със сървъра.

Клиентите могат да се свързват един с друг (peer-to-peer communication). За целта е нужно всеки да реализира „мини сървър“ при себе си. Този мини-сървър трябва да може да обработва командата download, описана по-долу. Можете да изберете дали мини-сървърът да обработва само една заявка за изтегляне или много паралелни заявки (ако е една - докато тя бива обработвана, оставащите заявки за изтегляне към мини-сървъра трябва да чакат).

Клиентът също така съхранява при себе си съответствие <*username* – *user* *IP:port*>.
Пример:
```
Pesho123 – 127.0.0.1:1234
Gosho321 – 127.0.0.1:2314
```
За да получи клиентът тази информация, той регулярно (през 30 секунди) пита сървъра за регистрираните потребители и техните адреси. Данните се записват във файл в указния по-горе формат.

### Клиент

Клиентът изпълнява следните команди:
1.	`register <user> <file1, file2, file3, …., fileN>` - позволява на клиентите да „обявят“ кои файлове са налични за сваляне от тях. Чрез параметъра username, потребителят може да зададе свое уникално име (името, с което сървърът ще асоциира съответното IP).

2.	`unregister <user> <file1, file2, file3, …., fileN>` - потребителят обявява, че от него вече не могат да се свалят файловете <file1, file2, file3, …., fileN>.

    ***Забележка***: За простота, не се интересуваме от security аспектите на решението, т.е. не е нужно да реализирате автентикация, която да гарантира, че даден потребител може да отрегистрира само файловете, които самият той е регистрирал.

3.	`list-files` – връща файловете, налични за изтегляне, и потребителите, от които могат да бъдат изтеглени.
4.	`download <user> <path to file on user> <path to save>` - изтегля дадения файл от съответния потребител.

### Сървър

Сървърът трябва да може да обработва изброените команди по подходящ начин:
1.	При получаване на `register <user> <file1, file2, file3, …., fileN>`, сървърът обновява информацията за този потребител (добавя информация, че тези файлове са налични за сваляне от съответния потребител).
2.	При получаване на `unregister <user> <file1, file2, file3, …., fileN>`, обновява информацията за този потребител (съответните файлове вече не са налични за сваляне от този потребител).
3.	При получаване на `list-files`, връща регистрираните в сървъра файлове във формат `<user> : <path to file>`
4.	При изпълняването на `download <user> <path to file on user> <path to save>`, не се случва комуникация с централния сървър.

    **a.**	Клиентът определя *IP адреса* и *порта* на потребителя, от който може да бъде изтеглен даденият файл (от локалния си mapping).

    **b.**	Клиентът изпраща командата на мини-сървъра на потребителя, определен в стъпка (а)

    **c.**	Мини-сървърът изпраща файла

    **d.**	След като потребителят получи файла, автоматично изпълнява командата register  <user> <path to saved file>. По този начин информацията в главния сървър за потребителите, притежаващи този файл, се обновява.


Една подсказваща **диаграма**:

![class-diagram](../images/peer-to-peer.png?raw=true)

### Submission

Качете `.zip` архив на познатите папки `src`, `test` и `resources` (опционално, ако имате допълнителни файлове, които не са .java) в sapera.org.
Там няма да има автоматизирани тестове.
Проектът ви трябва да е качен в грейдъра не по-късно от 18:00 в деня преди датата на защитата.

Успех!

