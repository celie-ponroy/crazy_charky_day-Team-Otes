<?php

namespace charly\infrastructure\repository\interfaces;

interface ServiceRepositoryInterface
{
    public function getServicesByUserId(string $userid): array;

}