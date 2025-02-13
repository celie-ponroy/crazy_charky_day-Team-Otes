<?php
namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Exception;

class GetSalaries extends AbstractAction
{
    protected UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            // Récupération des salaires
            $salaries = $this->userService->getSalaries();

            // Vérification si des salaires ont été trouvés
            if (empty($salaries)) {
                return JsonRenderer::render($rs, 404, ['error' => 'Aucun salaire trouvé.']);
            }

            return JsonRenderer::render($rs, 200, $salaries);

        } catch (Exception $e) {
            // Gestion des erreurs internes
            return JsonRenderer::render($rs, 500, ['error' => 'Erreur interne du serveur.']);
        }
    }
}
