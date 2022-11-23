# DoGet - Servlet

Primo passo, scaricare [Apache Tomcat versione 9](https://tomcat.apache.org/download-90.cgi)
scegliere il formato del download, .zip, .tar etc
dopo aver decompresso il file scaricato (nel caso di Linux, windows dovrebbe avere un installer/wizzard)
entrare nella directory di tomcat/bin/ ed eseguire lo script di avvio, ./startup.sh
per spegnere tomcat, ./shutdown.sh 

per creare web apps, bisogna crere una nuova directory nella directory:
/path-to-tomcat/webapps/

la directory creata dovra' avere una struttura specifica, in particolare deve contenere
la directory 'WEB-INF', dentro di essa devono risiederci la directory 'classes' dove andra'
messo il bytecode delle servlet da girare e sempre dentro 'WEB-INF' ma fuori da 'classes' il
file web.xml, dove risiedera' il mapping tra servlet e url-path 

dopo aver impostato/creato bene il tutto, e' altamente consigliabile riavviare tomcat.

nel file di configurazione, situato in /path-to-tomcat/conf/server.xml 
cambiare la porta (default 8080) a quella desiderata

dopo aver riavviato Tomcat, andare sul browser, se si hosta localmente
ed immettere [localhost:8080], se trovate una welcome page di tomcat, allora 
il servlet-engine e' partito, se volete usare le vostre servlet create 
[localhost:8080/DoGet/getPage] {il nome della servlet va incluso}