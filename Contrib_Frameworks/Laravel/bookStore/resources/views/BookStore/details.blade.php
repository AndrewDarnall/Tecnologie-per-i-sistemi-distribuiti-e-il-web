@extends('layout')
@section('title','Book Details')
@section('content')
<h1 class="display-1 sofia"> Details for: {{$db->title}} </h1>
<br>
<table class="table table-striped">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Book Seeries</th>
        <th>Rating</th>
        <th>Price</th>
    </tr>
    <tr>
        <td>{{$db->title}}</td>
        <td>{{$db->author}}</td>
        <td>{{$db->book_seeries}}</td>
        <td>{{$db->rating}}</td>
        <td>{{$db->price}}</td>
    </tr>
</table>
<br>
<br>
<form action="/books/{{$db->id}}/edit" method="GET">
    {{csrf_field()}}
    <input class="form-control btn btn-primary" type="submit" value="Edit Book"/>
</form>
<br>
@endsection
@section('home')
<form action="/" method="GET">
    <input class="form-control btn btn-primary" type="submit" value="Home"/>
</form>
@endsection