import React from 'react';
import ReactDOM from 'react-dom/client';

const myFirstElement = <h1>Hello from REACT!</h1>;

const root = ReactDOM.createRoot(document.getElementById( 'root' ));
root.render(myFirstElement);

const arr = ['C', 'C++', 'Java', 'JavaScript','Haskell','Erlang','Ada','Perl','php','Kotlin','Scala','Fortran','Pascal','C#','Objective-C','Swing','Dart','PL/SQL'];

const myList = arr.map((item) => <p>{item}</p>);

// To have the changes take effect --> render them

// to render: root.render(myList);

/**
 * Remember that REACT uses the virtual DOM first
 */