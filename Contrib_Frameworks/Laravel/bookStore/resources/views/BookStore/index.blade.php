@extends('layout')
@section('title','Home Page')
@section('content')
<h1 class="display-1 sofia">Odysseus' Book Store</h1>    
<br>
<br>
<img class="img-rounded" src="https://images.theconversation.com/files/184474/original/file-20170904-17903-1b267s2.jpg?ixlib=rb-1.1.0&amp;q=20&amp;auto=format&amp;w=320&amp;fit=clip&amp;dpr=2&amp;usm=12&amp;cs=strip" alt="Odysseus img">
<br>
<br>
<form action="/books" method="GET">
    <input class="form-control btn btn-primary" type="submit" value="Browser Books"/>
</form>
@endsection