# Versione Java

La complicazione di questa versione stava nel pensare in oggetti.
La soluzione ha 4 classi, Main, Thread1, Thread2 e Shared.
I Threads faranno a turno, come richiesto dal testo per accedere 
all'oggetto condiviso e bisogna fare attenzione poiche' se non
si accede appropiratamente javac solleva una serie di eccezzioni e se non
si chiama appropriatamete wait e notify allora si avranno deadlocks.