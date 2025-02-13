<?php
namespace charly\core\service;

use charly\infrastructure\repository\interfaces\ServiceRepositoryInterface;
use charly\core\service\interfaces\ServiceServiceInterface;

class ServiceService implements ServiceServiceInterface
{   
    private ServiceRepositoryInterface $serviceRepository;

    public function __construct(ServiceRepositoryInterface $serviceRepository)
    {
        $this->serviceRepository = $serviceRepository;
    }

    public function getServicesByUserId(string $userid): array
    {
        try {
            return $this->serviceRepository->getServicesByUserId($userid);
        } catch (\Exception $e) {
            throw new \Exception($e->getMessage());
        }
    }
}