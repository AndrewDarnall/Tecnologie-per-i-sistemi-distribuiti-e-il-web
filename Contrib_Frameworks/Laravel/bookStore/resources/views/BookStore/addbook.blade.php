@extends('layout')
@section('title','Add Book')
@section('content')
<h1 class="display-1 sofia"> Add Book Details </h1>
<br>
<br>
<form action="/books" method="POST">
    <!-- Senza il token csrf l'app non accetta richeste POST ~e meno male!~ -->
    {{csrf_field()}}
    <div class="mb-3 mt-3">
        <label class="display-3 sofia" id="space"> Title </label>
        <input class="form-control" type="text" name="title" placeholder="BookTitle">
    </div>
    <div class="mb-3 mt-3">
        <label class="display-3 sofia" id="space">Author</label>
        <input class="form-control" type="text" name="author" placeholder="Author">
    </div>
    <div class="mb-3 mt-3">
        <label class="display-3 sofia" id="space">Book Seeries</label>
        <input class="form-control" type="text" name="book_seeries" placeholder="Seeries">
    </div>
    <div class="mb-3 mt-3">
        <label class="display-3 sofia" id="space">Rating</label>
        <input class="form-control" type="text" name="rating" placeholder="Rating">
    </div>
    <div class="mb-3 mt-3">
        <label class="display-3 sofia" id="space">Price</label>
        <input class="form-control" type="text" name="price" placeholder="Price">
    </div>
    <br>
    <br>
    <input class="form-control btn btn-primary" type="submit" value="Add Book">
</form>
<br>
@endsection
@section('home')
<form action="/" method="GET">
    <input class="form-control btn btn-primary" type="submit" value="Home"/>
</form>
@endsection