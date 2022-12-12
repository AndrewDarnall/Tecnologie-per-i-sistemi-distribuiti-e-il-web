# Motivo esmempio

Comunicando tra front end con JavaScript e back end con php e JavaScript ho 
riscontrato problemi sia con la Fetch API che con la XMLHttpRequest (XHR)
Ottenevo 'failed access control checks' come errore sebbene il codice fosse
perfetto, abilitando l'opzione '__**cors**__' per il Cross Origin Resource Sharing
sia su node.js che su php, il tutto si e' risolto.