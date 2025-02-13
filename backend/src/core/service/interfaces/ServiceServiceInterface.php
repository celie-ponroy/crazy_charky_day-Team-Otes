<?php
namespace charly\core\service\interfaces;

interface ServiceServiceInterface
{
    public function getServicesByUserId(string $userid): array;
}