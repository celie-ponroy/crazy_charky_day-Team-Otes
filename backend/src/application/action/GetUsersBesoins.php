<?php
namespace charly\application\action;

use charly\core\service\interfaces\BesoinServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use charly\application\renderer\JsonRenderer;


class GetUsersBesoins extends AbstractAction
{
    protected BesoinServiceInterface $besoinService;

    public function __construct(BesoinServiceInterface $besoinService)
    {
        $this->besoinService = $besoinService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
 
        $data = $rq->getParsedBody();

        
        if (!isset($data['userId'])) {
            return JsonRenderer::render($rs, 400, ['error' => 'Le paramètre userId est requis dans le corps de la requête.']);
        }

        $userId = $data['userId'];

        

        try {
            // Récupérer les besoins du user
            $needs = $this->besoinService->getUserBesoins($userId);

            // Vérifier si des besoins existent pour ce user
            if (empty($needs)) {
                return JsonRenderer::render($rs, 404, ['error' => 'Aucun besoin trouvé pour ce user.']);
            }

            // Affichage des besoins
            return JsonRenderer::render($rs, 200, $needs);

        } catch (\Exception $e) {
            return JsonRenderer::render($rs, 500, ['error' => 'Erreur interne du serveur.']);
        }
    }
}
