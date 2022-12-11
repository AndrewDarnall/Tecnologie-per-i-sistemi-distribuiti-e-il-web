# Node.js

E' un runtime basato sul motore V8 JavaScript di google, praticamente un wrapper 
che facilita la comunicazione tra front-end e back-end rendendo la comunicazione
tra di essi uniforme, usano lo stesso linguaggio, JavaScript e lo stesso
formato di rappresentazione dei dati, ovvero JSON.

### rquire( 'http' ) 

E' una chiamata che importa moduli predefiniti del runtime, e' equivalente 
a HttpServlet

### Tipo di programmazione

La programmazione e' logicamente equivalente a quanto visto con le servlet
ovvero ad eventi

Infatti otteniamo gli oggetti request e response proprio come HttpServletRequest e HttpServletResponse

infine la chiamatat listen(8088) imposta il port su cui ascoltare

gli altri sorgenti mostrano altri esempi di backend

Il font end e backend essenzialmente comunicano __**tramite sockets**__.