<script setup>
import { RouterLink, RouterView } from 'vue-router';
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <header class="bg-white shadow-md">
      <div class="mx-auto px-4 py-6 flex justify-between items-center">
        <nav class="flex space-x-4">
          <RouterLink to="/" class="text-indigo-600 hover:text-indigo-800 font-semibold">PerfectMatch</RouterLink>
          <RouterLink to="/besoins" class="text-indigo-600 hover:text-indigo-800 font-semibold">Besoins</RouterLink>
          <RouterLink to="/besoins/add" class="text-indigo-600 hover:text-indigo-800 font-semibold">Ajouter un besoin</RouterLink>
        </nav>
      </div>
    </header>

    <main class="mx-auto px-4 py-8">
      <RouterView />
    </main>
  </div>
</template>


