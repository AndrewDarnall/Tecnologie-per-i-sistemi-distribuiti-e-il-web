@extends('layout')
@section('title','Book Magazine')
@section('content')
<h1 class="display-1 sofia"> Here are some of out finest books </h1>    
<br>
<br>
<table class="table table-striped">
    <tr>
        <th>Title</th>
    </tr>
    @foreach ($db as $item)
        <tr>
            <td><a href="/books/{{$item->id}}">{{$item->title}}</a></td>
        </tr>
    @endforeach
</table>
<br>
<form action="/books/create" method="GET">
    <input class="form-control btn btn-primary" type="submit" value="Add Book"/>
</form>
<br>
@endsection
@section('home')
<form action="/" method="GET">
    <input class="form-control btn btn-primary" type="submit" value="Home"/>
</form>
@endsection